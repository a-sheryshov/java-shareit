package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class ItemDto extends AbstractEntityDto {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final int MAX_NAME_LEN = 50;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";

    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    private Long ownerId;
    private Boolean available;
    private Long requestId;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}
