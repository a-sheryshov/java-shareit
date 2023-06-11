package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.Size;
import java.util.List;

import static ru.practicum.shareit.item.validation.ItemValidationConstants.*;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class ItemDto extends AbstractEntityDto {
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    private Long ownerId;
    private Boolean available;
    private ItemRequest request;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}
