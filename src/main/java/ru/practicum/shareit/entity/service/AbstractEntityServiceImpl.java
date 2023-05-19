package ru.practicum.shareit.entity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.entity.mapper.Mapper;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.entity.storage.Storage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEntityServiceImpl<E extends AbstractEntity, D extends AbstractEntityDto>
        implements Service<D> {
    private final Storage<E> storage;
    private final Mapper<E, D> mapper;
    private final Class<E> type;
    @Override
    public D create(@Valid D dto) {
        E result = storage.create(mapper.toObject(dto));
        log.info("{} added: {}", type.getSimpleName(), result.getId());
        return mapper.toDto(result);
    }
    @Override
    public D update(@Valid @Positive Long id, @Valid D dto) {
        E objToUpdate = storage.read(id);
        BeanUtils.copyProperties(dto, objToUpdate, getIdAndNullPropertyNames(dto));
        E result = storage.update(objToUpdate);
        log.info("{} updated: {}", type.getSimpleName(), id);
        return mapper.toDto(result);
    }
    public D read(@Valid @Positive Long id) {
        return mapper.toDto(storage.read(id));
    }
    @Override
    public List<D> readAll() {
        return storage.readAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(@Valid @Positive Long id) {
        storage.delete(id);
        log.info("{} deleted: {}", type.getSimpleName(), id);
    }
    public static String[] getIdAndNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        emptyNames.add("id");
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
