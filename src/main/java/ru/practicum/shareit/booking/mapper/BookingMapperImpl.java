package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.model.Booking;

@Component
public class BookingMapperImpl implements BookingMapper {
    @Override
    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .item(booking.getItem())
                .build();
    }

    @Override
    public Booking toObject(BookingDto bookingDto) {
        return Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .booker(bookingDto.getBooker())
                .status(bookingDto.getStatus())
                .item(bookingDto.getItem())
                .build();
    }

    @Override
    public Booking toObject(BookingShortDto bookingShortDto) {
        return Booking.builder()
                .id(bookingShortDto.getId())
                .start(bookingShortDto.getStart())
                .end(bookingShortDto.getEnd())
                .build();
    }

    @Override
    public BookingShortDto toDtoShort(Booking booking) {
        return BookingShortDto.builder()
                .id(booking.getId())
                .bookerId(booking.getBooker().getId())
                .itemId(booking.getItem().getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }
}