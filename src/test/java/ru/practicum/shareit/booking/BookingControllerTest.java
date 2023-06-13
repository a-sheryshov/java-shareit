package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    public static final Long ID = 1L;
    public static final String FROM_VALUE = "0";
    public static final String SIZE_VALUE = "20";
    public static final String FROM_PARAM = "from";
    public static final String SIZE_PARAM = "size";
    public static final String STATE_VALUE = "ALL";
    public static final String STATE_PARAM = "state";
    public static final String APPROVED_VALUE = "true";
    public static final String APPROVED_PARAM = "approved";
    public static final String USER_ID_HEADER = "X-Sharer-User-Id";

    public static final LocalDateTime START_DATE = LocalDateTime.now().plusMinutes(1);
    public static final LocalDateTime END_DATE = START_DATE.plusMinutes(10);

    @MockBean
    BookingService bookingService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    @Test
    public void createBookingTest() throws Exception {
        BookingShortDto inputDto = generateInputDto();
        BookingDto responseDto = generateDetailedResponseDto(ID, inputDto);

        when(bookingService.create(any(BookingShortDto.class), any(Long.class)))
                .thenReturn(responseDto);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(inputDto))
                        .header(USER_ID_HEADER, ID)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(responseDto.getId()),  Long.class))
                .andExpect(jsonPath("$.item", is(responseDto.getItem()),  Item.class));

        verify(bookingService, times(1))
                .create(any(BookingShortDto.class), any(Long.class));
    }

    @Test
    public void approveBookingTest() throws Exception {
        BookingDto responseDto = generateResponseDto(ID);

        when(bookingService.approve(any(Long.class), any(Long.class), any(Boolean.class)))
                .thenReturn(responseDto);

        mvc.perform(patch("/bookings/1")
                        .param(APPROVED_PARAM, APPROVED_VALUE)
                        .header(USER_ID_HEADER, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(responseDto.getId()), Long.class));

        verify(bookingService, times(1))
                .approve(any(Long.class), any(Long.class), any(Boolean.class));
    }

    @Test
    public void readByIdTest() throws Exception {
        BookingDto responseDto = generateResponseDto(ID);

        when(bookingService.readById(any(Long.class), any(Long.class)))
                .thenReturn(responseDto);

        mvc.perform(get("/bookings/1")
                        .header(USER_ID_HEADER, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(responseDto.getId()), Long.class));

        verify(bookingService, times(1))
                .readById(any(Long.class), any(Long.class));
    }

    @Test
    public void readAllBookingsTest() throws Exception {
        when(bookingService.readAllByUser(any(Long.class), any(String.class),  any(Integer.class), any(Integer.class)))
                .thenReturn(new ArrayList<>());

        mvc.perform(get("/bookings")
                        .header(USER_ID_HEADER, ID)
                        .param(FROM_PARAM, FROM_VALUE)
                        .param(SIZE_PARAM, SIZE_VALUE)
                        .param(STATE_PARAM, STATE_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(bookingService, times(1))
                .readAllByUser(any(Long.class), any(String.class), any(Integer.class), any(Integer.class));
    }

    @Test
    public void readAllTest() throws Exception {
        when(bookingService.readAllByOwner(any(Long.class), any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(new ArrayList<>());

        mvc.perform(get("/bookings/owner")
                        .header(USER_ID_HEADER, ID)
                        .param(FROM_PARAM, FROM_VALUE)
                        .param(SIZE_PARAM, SIZE_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(bookingService, times(1))
                .readAllByOwner(any(Long.class),any(String.class), any(Integer.class), any(Integer.class));
    }

    private BookingShortDto generateInputDto() {
        return BookingShortDto.builder().itemId(ID).build();
    }

    private BookingDto generateDetailedResponseDto(Long id, BookingShortDto inputDto) {
        BookingDto result = BookingDto.builder().id(id).build();
        Item item = new Item();
        item.setId(ID);
        item.setName("item");
        item.setDescription("item description");
        item.setAvailable(true);
        result.setItem(item);
        result.setStart(START_DATE);
        result.setEnd(END_DATE);
        return result;
    }

    private BookingDto generateResponseDto(Long id) {
        BookingDto result = BookingDto.builder().id(id).build();
        result.setBooker(new User());
        result.setItem(new Item());
        return result;
    }
}