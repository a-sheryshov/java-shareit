package ru.practicum.shareit.booking.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.exception.BookingException;
import ru.practicum.shareit.booking.exception.UnsupportedStatusException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingStatus.*;

@AllArgsConstructor
@Validated
@Service
public class BookingServiceImpl implements BookingService {
    private final Sort sort = Sort.by(Sort.Direction.DESC, "start");
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    @Override
    public BookingDto create(@Valid BookingShortDto bookingShortDto, @Positive Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User " + userId + " not found"));
        Item item = itemRepository.findById(bookingShortDto.getItemId())
                .orElseThrow(() -> new ObjectNotFoundException("Item " + bookingShortDto.getItemId()
                        + " not found"));
        if (item.getOwner().getId().equals(userId)) {
            throw new ObjectNotFoundException("Item " + bookingShortDto.getItemId()
                    + " is your own");
        }
        if (!item.getAvailable()) {
            throw new BookingException("Item unavailable");
        }
        Booking booking = bookingMapper.toObject(bookingShortDto);
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStatus(WAITING);
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Transactional
    @Override
    public BookingDto approve(@Positive Long bookingId, @Positive Long userId, @NotNull Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Booking " + bookingId + " not found"));
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new ObjectNotFoundException("Booking " + bookingId + " not found");
        }
        if (!booking.getStatus().equals(WAITING)) {
            throw new BookingException("Approved/Denied already");
        }
        if (approved) {
            booking.setStatus(APPROVED);
        } else {
            booking.setStatus(REJECTED);
        }
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingDto> readAllByOwner(@Positive Long userId, String state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User " + userId + " not found"));
        List<Booking> bookingDtoList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwner(user, sort));
                break;
            case "CURRENT":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now(), sort));
                break;
            case "PAST":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndEndBefore(user,
                        LocalDateTime.now(), sort));
                break;
            case "FUTURE":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndStartAfter(user,
                        LocalDateTime.now(), sort));
                break;
            case "WAITING":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndStatusEquals(user, WAITING, sort));
                break;
            case "REJECTED":
                bookingDtoList.addAll(bookingRepository.findAllByItemOwnerAndStatusEquals(user, REJECTED, sort));
                break;
            default:
                throw new UnsupportedStatusException("Unknown state: UNSUPPORTED_STATUS");
        }

        return bookingDtoList.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingDto> readAllByUser(@Positive Long userId, String state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User " + userId + " not found"));
        List<Booking> bookingDtoList = new ArrayList<>();
        switch (state) {
            case "ALL":
                bookingDtoList.addAll(bookingRepository.findAllByBooker(user, sort));
                break;
            case "CURRENT":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndStartBeforeAndEndAfter(user,
                        LocalDateTime.now(), LocalDateTime.now(), sort));
                break;
            case "PAST":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndEndBefore(user,
                        LocalDateTime.now(), sort));
                break;
            case "FUTURE":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndStartAfter(user, LocalDateTime.now(), sort));
                break;
            case "WAITING":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, WAITING, sort));
                break;
            case "REJECTED":
                bookingDtoList.addAll(bookingRepository.findAllByBookerAndStatusEquals(user, REJECTED, sort));
                break;
            default:
                throw new UnsupportedStatusException("Unknown state: UNSUPPORTED_STATUS");
        }

        return bookingDtoList.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public BookingDto readById(@Positive Long bookingId, @Positive Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Booking " + bookingId + " not found"));
        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(booking.getItem().getOwner().getId())) {
            throw new ObjectNotFoundException("Booking " + bookingId + " not found");
        }
        return bookingMapper.toDto(booking);
    }
}