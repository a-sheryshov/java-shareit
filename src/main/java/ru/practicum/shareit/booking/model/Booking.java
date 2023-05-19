package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Booking extends AbstractEntity {
    @NotNull(message = "Booking start time is mandatory")
    private LocalDateTime start;
    @NotNull(message = "Booking end time is mandatory")
    private LocalDateTime end;
    @NotNull(message = "Item ID is mandatory")
    @Positive(message = "Item ID should be positive")
    private Long itemId;
    @NotNull(message = "Booker ID is mandatory")
    @Positive(message = "Booker ID should be positive")
    private Long booker;
    @NotNull(message = "Booking status is mandatory")
    private BookingStatus status;

    @AssertTrue(message = "Booking end time should be after start time")
    private boolean isValidEndTime() {
        return end.isAfter(start);
    }
}
