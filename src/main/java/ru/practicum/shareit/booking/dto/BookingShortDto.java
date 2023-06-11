package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class BookingShortDto extends AbstractEntityDto {

    @NotNull
    @Future
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;

    private Long itemId;

    private Long bookerId;

    @AssertTrue(message = "Booking end time should be after start time")
    private boolean isValidEndTime() {
        if (start != null && end != null) {
            return end.isAfter(start);
        } else return false;
    }
}