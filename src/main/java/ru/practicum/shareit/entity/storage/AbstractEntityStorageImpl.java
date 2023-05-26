package ru.practicum.shareit.entity.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEntityStorageImpl<E extends AbstractEntity> implements Storage<E> {
    private final Class<E> type;
    private final Map<Long, E> kvStorage = new HashMap<>();
    private long counter = 1L;

    @Override
    public E create(@Valid E obj) {
        obj.setId(counter);
        counter++;
        kvStorage.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public E read(Long id) {
        if (!kvStorage.containsKey(id)) {
            String message = type.getSimpleName() + " with ID " + id + " not found.";
            throw new ObjectNotFoundException(message);
        }
        return kvStorage.get(id);
    }

    @Override
    public List<E> readAll() {
        return new ArrayList<>(kvStorage.values());
    }

    @Override
    public E update(@Valid E obj) {
        if (!kvStorage.containsKey(obj.getId())) {
            log.info(type.getSimpleName() + " with ID " + obj.getId() + " not found. Creating new.");
            return create(obj);
        }
        kvStorage.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public void delete(Long id) {
        if (!kvStorage.containsKey(id)) {
            String message = type.getSimpleName() + " with ID " + id + " not found.";
            throw new ObjectNotFoundException(message);
        }
        kvStorage.remove(id);
    }

}
