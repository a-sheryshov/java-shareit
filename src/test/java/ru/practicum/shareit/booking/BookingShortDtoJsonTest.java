package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingShortDtoJsonTest {
    @Autowired
    JacksonTester<BookingShortDto> json;

    @Test
    void testBookingDto() throws Exception {
        BookingShortDto bookingDto = BookingShortDto
                .builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 6, 12, 10, 10, 1))
                .end(LocalDateTime.of(2023, 6, 20, 10, 10, 1))
                .build();

        JsonContent<BookingShortDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start")
                .isEqualTo(LocalDateTime.of(2023, 6, 12, 10, 10, 1).toString());
        assertThat(result).extractingJsonPathStringValue("$.end")
                .isEqualTo(LocalDateTime.of(2023, 6, 20, 10, 10, 1).toString());
    }
}