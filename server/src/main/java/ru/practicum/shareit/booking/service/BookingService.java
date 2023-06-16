package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingShortDto bookingShortDto, Long userId);

    BookingDto approve(Long bookingId, Long userId, Boolean approved);

    List<BookingDto> readAllByOwner(Long userId, String state, int from, int size);

    List<BookingDto> readAllByUser(Long userId, String state, int from, int size);

    BookingDto readById(Long itemId, Long userId);
}
