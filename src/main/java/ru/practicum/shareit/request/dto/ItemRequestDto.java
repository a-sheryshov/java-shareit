package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.request.validation.ItemRequestValidationConstants.DESC_LEN_VALIDATION_ERR_MSG;
import static ru.practicum.shareit.request.validation.ItemRequestValidationConstants.MAX_DESCRIPTION_LEN;

@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class ItemRequestDto extends AbstractEntityDto {
    @NotBlank
    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;
    private LocalDateTime created;
    @Positive
    private Long requestorId;
    private List<ItemDto> items;
}