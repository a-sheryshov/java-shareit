package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.shareit.request.validation.ItemRequestValidationConstants.DESC_LEN_VALIDATION_ERR_MSG;
import static ru.practicum.shareit.request.validation.ItemRequestValidationConstants.MAX_DESCRIPTION_LEN;

@Getter
@SuperBuilder
public class ItemRequestDto extends AbstractEntity {
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    @Positive(message = "Requestor ID should be positive")
    private Long requestorId;
    private LocalDateTime created;
}