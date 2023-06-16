package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.entity.repository.Repository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends Repository<Booking> {

    Page<Booking> findAllByBooker(User booker, Pageable pageable);

    List<Booking> findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(Long bookerId, Long itemId,
                                                                          BookingStatus status, LocalDateTime end);

    Page<Booking> findAllByBookerAndStartBeforeAndEndAfter(User booker, LocalDateTime start, LocalDateTime end,
                                                           Pageable pageable);

    Page<Booking> findAllByBookerAndEndBefore(User booker, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByBookerAndStartAfter(User booker, LocalDateTime start, Pageable pageable);

    Page<Booking> findAllByBookerAndStatusEquals(User booker, BookingStatus status, Pageable pageable);

    Page<Booking> findAllByItemOwner(User owner, Pageable pageable);

    Page<Booking> findAllByItemOwnerAndStartBeforeAndEndAfter(User owner, LocalDateTime start, LocalDateTime end,
                                                              Pageable pageable);

    Page<Booking> findAllByItemOwnerAndEndBefore(User owner, LocalDateTime end, Pageable pageable);

    Page<Booking> findAllByItemOwnerAndStartAfter(User owner, LocalDateTime start, Pageable pageable);

    Page<Booking> findAllByItemOwnerAndStatusEquals(User owner, BookingStatus status, Pageable pageable);

    List<Booking> findAllByItemIdAndStartBeforeAndStatusNot(Long itemId, LocalDateTime now, BookingStatus status, Sort sort);

    List<Booking> findAllByItemIdAndStartAfterAndStatusNot(Long itemId, LocalDateTime now, BookingStatus status, Sort sort);
}
