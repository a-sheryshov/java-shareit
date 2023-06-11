package ru.practicum.shareit.booking.service;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface BookingService {
    BookingDto create(@Valid BookingShortDto bookingShortDto, @Positive Long userId);

    BookingDto approve(@Positive Long bookingId, @Positive Long userId, @NotNull Boolean approved);

    List<BookingDto> readAllByOwner(@Positive Long userId, String state);

    List<BookingDto> readAllByUser(@Positive Long userId, String state);

    BookingDto readById(@Positive Long itemId, @Positive Long userId);
}
