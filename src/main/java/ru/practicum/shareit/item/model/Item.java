package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static ru.practicum.shareit.item.validation.ItemValidationConstants.*;

@Data
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Item extends AbstractEntity {
    @NotBlank(message = "Name is mandatory")
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    @NotNull(message = "Status is mandatory")
    private Boolean available;
    @NotNull(message = "Owner is mandatory")
    @Positive(message = "Should be greater than 0")
    private Long owner;
    private ItemRequest request;
}
