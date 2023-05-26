package ru.practicum.shareit.user.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.entity.storage.AbstractEntityStorageImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;

@Repository
public class InMemoryUserStorageImpl extends AbstractEntityStorageImpl<User> implements UserStorage {
    private final ItemStorage itemStorage;

    @Autowired
    public InMemoryUserStorageImpl(ItemStorage itemStorage) {
        super(User.class);
        this.itemStorage = itemStorage;
    }

    @Override
    public void delete(Long userId) {
        itemStorage.readAll(userId).stream().map(Item::getId).forEach(itemStorage::delete);
        super.delete(userId);
    }
}
