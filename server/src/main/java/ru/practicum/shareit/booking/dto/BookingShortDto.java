package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookingShortDto extends AbstractEntityDto {

    private LocalDateTime start;

    private LocalDateTime end;

    private Long itemId;

    private Long bookerId;

}