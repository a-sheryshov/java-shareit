package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.entity.storage.AbstractEntityStorageImpl;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class InMemoryItemStorageImpl extends AbstractEntityStorageImpl<Item> implements ItemStorage {

    public InMemoryItemStorageImpl() {
        super(Item.class);
    }

    @Override
    public List<Item> readAll(Long userId) {
        return readAll().stream().filter(item -> userId.equals(item.getOwner())).collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String description) {
         return readAll().stream().filter(item -> searchPredicate(description, item)).collect(Collectors.toList());
    }

    private Boolean searchPredicate(String text, Item item) {
        return item.getName().toLowerCase().contains(text.toLowerCase()) ||
                item.getDescription().toLowerCase().contains(text.toLowerCase()) && item.getAvailable();
    }
}
