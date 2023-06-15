package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private static final String INFO_LOG_MSG_RGX = "Request '{}' to '{}', objectId: {}";
    BookingService bookingService;

    @PostMapping
    public BookingDto create(@RequestBody BookingShortDto bookingShortDto,
                             @RequestHeader("X-Sharer-User-Id") Long userId, HttpServletRequest request) {
        BookingDto result = bookingService.create(bookingShortDto, userId);
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), result.getId());
        return result;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestParam Boolean approved, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), bookingId);
        return bookingService.approve(bookingId, userId, approved);
    }

    @GetMapping("/owner")
    public List<BookingDto> readAllByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestParam(defaultValue = "ALL") String state,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), "N/A");
        return bookingService.readAllByOwner(userId, state, from, size);
    }

    @GetMapping
    public List<BookingDto> readAllByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @RequestParam(defaultValue = "ALL") String state,
                                          @RequestParam(defaultValue = "0") int from,
                                          @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), "N/A");
        return bookingService.readAllByUser(userId, state, from, size);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getById(@PathVariable Long bookingId,
                              @RequestHeader("X-Sharer-User-Id") Long userId, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), bookingId);
        return bookingService.readById(bookingId, userId);
    }

}
