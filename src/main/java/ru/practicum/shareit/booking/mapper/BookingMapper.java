package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.entity.mapper.Mapper;

public interface BookingMapper extends Mapper<Booking, BookingDto> {
    Booking toObject(BookingShortDto bookingShortDto);

    BookingShortDto toDtoShort(Booking booking);
}
