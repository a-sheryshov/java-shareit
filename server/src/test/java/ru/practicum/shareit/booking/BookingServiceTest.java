package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.booking.exception.BookingException;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingServiceTest {
    private final BookingService bookingService;

    @Test
    @Order(0)
    @Sql(value = { "/test-schema.sql", "/users-create-test.sql", "/item-create-test.sql" })
    void createTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        Optional<BookingDto> bookingDto = Optional.of(bookingService.create(incomeDto, 2L));

        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrProperty("item");
                    assertThat(i.getItem()).hasFieldOrPropertyWithValue("name", "item1");
                    assertThat(i).hasFieldOrProperty("booker");
                    assertThat(i.getBooker()).hasFieldOrPropertyWithValue("email", "user1@email.ru");
                    assertThat(i).hasFieldOrPropertyWithValue("status", BookingStatus.WAITING);
                });
    }

    @Test
    @Order(1)
    void approveTest() {
        Optional<BookingDto> bookingDto = Optional.of(bookingService.approve(1L, 1L, true));

        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrProperty("item");
                    assertThat(i.getItem()).hasFieldOrPropertyWithValue("name", "item1");
                    assertThat(i).hasFieldOrProperty("booker");
                    assertThat(i.getBooker()).hasFieldOrPropertyWithValue("email", "user1@email.ru");
                    assertThat(i).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
                });
    }

    @Test
    @Order(2)
    void readByIdTest() {
        Optional<BookingDto> bookingDto = Optional.of(bookingService.readById(1L, 2L));

        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 1L);
                    assertThat(i).hasFieldOrProperty("item");
                    assertThat(i.getItem()).hasFieldOrPropertyWithValue("name", "item1");
                    assertThat(i).hasFieldOrProperty("booker");
                    assertThat(i.getBooker()).hasFieldOrPropertyWithValue("email", "user1@email.ru");
                    assertThat(i).hasFieldOrPropertyWithValue("status", BookingStatus.APPROVED);
                });
    }

    @Test
    @Order(3)
    void readByIdFailTest() {
        assertThrows(ObjectNotFoundException.class, () -> bookingService.readById(1L, 3L));
    }

    @Test
    @Order(4)
    void approveFailTest() {
        assertThrows(BookingException.class, () -> bookingService.approve(1L, 1L, true));
    }

    @Test
    @Order(5)
    void readByIdNotExistTest() {
        assertThrows(ObjectNotFoundException.class, () -> bookingService.readById(10L, 1L));
    }

    @Test
    @Order(6)
    void createFailTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(2L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();
        assertThrows(BookingException.class, () -> bookingService.create(incomeDto, 2L));
    }

    @Test
    @Order(7)
    void rejectTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(3L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();
        bookingService.create(incomeDto, 2L);

        Optional<BookingDto> bookingDto = Optional.of(bookingService.approve(2L, 1L, false));

        assertThat(bookingDto)
                .isPresent()
                .hasValueSatisfying(i -> {
                    assertThat(i).hasFieldOrPropertyWithValue("id", 2L);
                    assertThat(i).hasFieldOrProperty("item");
                    assertThat(i.getItem()).hasFieldOrPropertyWithValue("name", "item3");
                    assertThat(i).hasFieldOrProperty("booker");
                    assertThat(i.getBooker()).hasFieldOrPropertyWithValue("email", "user1@email.ru");
                    assertThat(i).hasFieldOrPropertyWithValue("status", BookingStatus.REJECTED);
                });
    }

    @Test
    @Order(8)
    void createWithNotExistItemTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(100L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();
        assertThrows(ObjectNotFoundException.class, () -> bookingService.create(incomeDto, 2L));
    }

    @Test
    @Order(8)
    void createWithNotExistUserTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        assertThrows(ObjectNotFoundException.class, () -> bookingService.create(incomeDto, 200L));
    }

    @Test
    @Order(9)
    @Sql(value = { "/all-bookings-create-test.sql" })
    void approveNotByOwnerTest() {
        assertThrows(ObjectNotFoundException.class, () -> bookingService.approve(3L, 2L, true));
    }

    @Test
    @Order(10)
    void createFromUserTest() {
        BookingShortDto incomeDto = BookingShortDto.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        assertThrows(ObjectNotFoundException.class, () -> bookingService.create(incomeDto, 1L));
    }

    @Test
    @Order(11)
    void readAllByOwnerTest() {
        int from = 0;
        int size = 10;
        String state = "ALL";
        long userId = 1L;
        List<BookingDto> bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(5);

        state = "CURRENT";
        bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .isEmpty();

        state = "PAST";
        bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(1);

        state = "FUTURE";
        bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(4);

        state = "WAITING";
        bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(1);

        state = "REJECTED";
        bookings = bookingService.readAllByOwner(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(2);
    }

    @Test
    @Order(11)
    void getAllByBookerTest() {
        int from = 0;
        int size = 10;
        String state = "ALL";
        long userId = 2L;
        List<BookingDto> bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(5);

        state = "CURRENT";
        bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .isEmpty();

        state = "PAST";
        bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(1);

        state = "FUTURE";
        bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(4);

        state = "WAITING";
        bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(1);

        state = "REJECTED";
        bookings = bookingService.readAllByUser(userId, state, from, size);
        Assertions.assertThat(bookings)
                .hasSize(2);
    }
}