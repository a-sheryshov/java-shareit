package ru.practicum.shareit.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import ru.practicum.shareit.booking.dto.BookingShortDto;

public class BookingShortDtoValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testBookingDtoValidationSuccess() {
        BookingShortDto dto = BookingShortDto
                .builder()
                .itemId(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().plusMinutes(1))
                .end(LocalDateTime.now().plusMinutes(5))
                .build();
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testBookingDtoValidationFailWhenStartInPast() {
        BookingShortDto dto = BookingShortDto
                .builder()
                .itemId(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().minusMinutes(1))
                .end(LocalDateTime.now().plusMinutes(5))
                .build();
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBookingDtoValidationFailWhenStartAndEndInPast() {
        BookingShortDto dto = BookingShortDto
                .builder()
                .itemId(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().minusMinutes(10))
                .end(LocalDateTime.now().minusMinutes(5))
                .build();
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testBookingDtoValidationFailWhenStartBeforeEnd() {
        BookingShortDto dto = BookingShortDto
                .builder()
                .itemId(1L)
                .bookerId(1L)
                .start(LocalDateTime.now().plusMinutes(10))
                .end(LocalDateTime.now().plusMinutes(5))
                .build();
        Set<ConstraintViolation<BookingShortDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

}
