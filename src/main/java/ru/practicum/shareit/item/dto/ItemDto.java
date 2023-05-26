package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static ru.practicum.shareit.item.validation.ItemValidationConstants.*;

@Getter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class ItemDto extends AbstractEntityDto {
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;

    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;

    private Boolean available;

    @Positive(message = "Should be greater than 0")
    private Long owner;
    private ItemRequest request;
}
