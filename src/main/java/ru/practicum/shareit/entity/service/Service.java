package ru.practicum.shareit.entity.service;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface Service<D extends AbstractEntityDto> {
    D read(@Valid @Positive final Long id);
    List<D> readAll();
    D create(@Valid final D dto);
    D update(@Valid @Positive Long id, @Valid final D dto);
    void delete(@Valid @Positive final Long id);
}
