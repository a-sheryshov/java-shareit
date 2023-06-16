package ru.practicum.shareit.entity.mapper;

import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.entity.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractEntityDto> {
    D toDto(E obj);

    E toObject(D dto);
}
