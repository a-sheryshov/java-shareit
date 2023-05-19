package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.entity.storage.Storage;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage extends Storage<Item> {
    List<Item> readAll(Long userId);

    List<Item> search(String query);

}
