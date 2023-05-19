package ru.practicum.shareit.entity.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Positive;

@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public abstract class AbstractEntity {
    @Positive(message = "Should be greater than 0")
    protected Long id;
}
