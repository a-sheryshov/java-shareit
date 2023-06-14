package ru.practicum.shareit.entity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.entity.mapper.Mapper;
import ru.practicum.shareit.entity.model.AbstractEntity;
import ru.practicum.shareit.entity.repository.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEntityServiceImpl<E extends AbstractEntity, D extends AbstractEntityDto>
        implements Service<D> {
    private final Repository<E> repository;
    private final Mapper<E, D> mapper;
    private final Class<E> type;

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

    @Override
    public D create(D dto) {
        E result = repository.save(mapper.toObject(dto));
        log.info("{} added: {}", type.getSimpleName(), result.getId());
        return mapper.toDto(result);
    }

    @Transactional
    @Override
    public D update(Long id, D dto) {
        E objToUpdate = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(type.getSimpleName()
                + id + " not found"));
        BeanUtils.copyProperties(dto, objToUpdate, getIdAndNullPropertyNames(dto));
        E result = repository.save(objToUpdate);
        log.info("{} updated: {}", type.getSimpleName(), id);
        return mapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public D read(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(type.getSimpleName()
                + id + " not found")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<D> readAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        log.info("{} deleted: {}", type.getSimpleName(), id);
    }

}
