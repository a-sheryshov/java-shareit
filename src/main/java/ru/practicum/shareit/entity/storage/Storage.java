package ru.practicum.shareit.entity.storage;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.Valid;
import java.util.List;
@Validated
public interface Storage<E extends AbstractEntity>{
    E create (@Valid E obj);
    E update (@Valid E obj);
    E read (Long id);
    List<E> readAll ();
    void delete(Long id);
}
