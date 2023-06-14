package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.practicum.shareit.booking.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.BookingStatus.WAITING;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private User user;

    private Item item;

    private User user2;

    private Booking booking;
    private static final LocalDateTime start = LocalDateTime.now().minusMinutes(10);
    private static final LocalDateTime end = LocalDateTime.now().plusMinutes(10);

    @BeforeEach
    void init() {

        user = User.builder()
                .name("name")
                .email("email@email.com")
                .build();

        item = Item.builder()
                .name("name")
                .description("description")
                .available(true)
                .owner(user)
                .build();

        user2 = User.builder()
                .name("name2")
                .email("email2@email.com")
                .build();

        booking = Booking.builder()
                .start(start)
                .end(end)
                .item(item)
                .booker(user2)
                .status(WAITING)
                .build();
    }

    @Test
    void findAllByBookerTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByBooker(user2, Pageable.ofSize(10)).stream().count(), equalTo(1L));
    }

    @Test
    void findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBeforeTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        booking.setStatus(APPROVED);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(user2.getId(),
                        item.getId(), APPROVED, LocalDateTime.now().plusMinutes(15)).size(),
                equalTo(1));
    }

    @Test
    void findAllByBookerAndStartBeforeAndEndAfterTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByBookerAndStartBeforeAndEndAfter(user2,
                LocalDateTime.now(), LocalDateTime.now(),
                Pageable.ofSize(10)).stream().count(), equalTo(1L));
    }

    @Test
    void findAllByItemOwnerAndEndBeforeTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByItemOwnerAndEndBefore(user, LocalDateTime.now().plusMinutes(15),
                Pageable.ofSize(10)).stream().count(), equalTo(1L));
    }

    @Test
    void findAllByItemOwnerAndStartAfterTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByItemOwnerAndStartAfter(user, LocalDateTime.now().minusMinutes(15),
                Pageable.ofSize(10)).stream().count(), equalTo(1L));
    }

    @Test
    void findAllByItemOwnerAndStatusEqualsTest() {
        userRepository.save(user);
        itemRepository.save(item);
        userRepository.save(user2);
        bookingRepository.save(booking);
        assertThat(bookingRepository.findAllByItemOwnerAndStatusEquals(user, WAITING, Pageable.ofSize(10))
                .stream().count(), equalTo(1L));
    }
}