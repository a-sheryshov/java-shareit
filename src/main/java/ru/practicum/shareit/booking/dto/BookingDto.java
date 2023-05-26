package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BookingDto extends AbstractEntityDto {
    private LocalDateTime start;
    private LocalDateTime end;
    @Positive(message = "Item ID should be positive")
    private Long itemId;
    @Positive(message = "Booker ID should be positive")
    private Long booker;
    private BookingStatus status;
}