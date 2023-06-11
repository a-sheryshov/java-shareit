package ru.practicum.shareit.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.shareit.booking.exception.BookingException;
import ru.practicum.shareit.booking.exception.UnsupportedStatusException;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.exception.CommentCreationException;
import ru.practicum.shareit.item.exception.ForbiddenException;
import ru.practicum.shareit.item.exception.NoUserIdHeaderException;
import ru.practicum.shareit.user.exception.EmailAlreadyInUseException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApplicationError onObjectNotFoundException(
            ObjectNotFoundException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintViolationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> {
                            String name = "";
                            for (Path.Node node : violation.getPropertyPath()) {
                                name = node.getName();
                            }
                            return new Violation(name,
                                    violation.getMessage()
                            );
                        }
                )
                .collect(Collectors.toList());
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Constraint violation exception: ");
        violations.forEach(violation -> logMessage.append(violation.getMessage()).append(", "));
        log.error(logMessage.toString());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApplicationError onEmailAlreadyInUseException(
            EmailAlreadyInUseException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler(NoUserIdHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApplicationError onNoUserIdHeaderException(
            NoUserIdHeaderException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApplicationError onForbiddenException(
            ForbiddenException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApplicationError onDataIntegrityViolationException(
            DataIntegrityViolationException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.CONFLICT.value(), "Data integrity violation");
    }

    @ExceptionHandler(BookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApplicationError onBookingException(
            BookingException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(UnsupportedStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApplicationError onUnsupportedStatusException(
            UnsupportedStatusException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(CommentCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApplicationError onCommentCreationException(
            CommentCreationException e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApplicationError onOtherException(
            Exception e
    ) {
        log.error(e.getMessage());
        return new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal error");
    }

}
