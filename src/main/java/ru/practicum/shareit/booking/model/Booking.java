package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Booking extends AbstractEntity {
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Booking start time is mandatory")
    private LocalDateTime start;
    @Column(name = "end_date", nullable = false)
    @NotNull(message = "Booking end time is mandatory")
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Item is mandatory")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "booker_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Booker is mandatory")
    private User booker;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @NotNull(message = "Booking status is mandatory")
    private BookingStatus status;

    @AssertTrue(message = "Booking end time should be after start time")
    private boolean isValidEndTime() {
        return end.isAfter(start);
    }
}
