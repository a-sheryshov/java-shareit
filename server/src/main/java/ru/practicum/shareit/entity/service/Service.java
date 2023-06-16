package ru.practicum.shareit.entity.service;

import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import java.util.List;

public interface Service<D extends AbstractEntityDto> {
    D read(final Long id);

    List<D> readAll();

    D create(final D dto);

    D update(Long id, final D dto);

    void delete(final Long id);
}
