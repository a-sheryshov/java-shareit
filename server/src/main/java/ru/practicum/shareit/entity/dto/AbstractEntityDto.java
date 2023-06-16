package ru.practicum.shareit.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;

@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public abstract class AbstractEntityDto {
    @Positive(message = "Should be greater than 0")
    protected Long id;
}
