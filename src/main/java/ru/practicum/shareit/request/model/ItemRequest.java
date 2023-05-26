package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.shareit.request.validation.ItemRequestValidationConstants.*;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemRequest extends AbstractEntity {

    @NotBlank(message = "Description is mandatory")
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    @NotNull(message = "Requestor ID is mandatory")
    @Positive(message = "Requestor ID should be positive")
    private Long requestorId;
    @NotNull(message = "Item request creation time is mandatory")
    private LocalDateTime created;
}
