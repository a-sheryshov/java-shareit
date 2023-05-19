package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.ForbiddenException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl extends AbstractEntityServiceImpl<Item, ItemDto> implements ItemService {
    final UserStorage userStorage;
    final ItemStorage itemStorage;
    final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemStorage itemStorage, UserStorage userStorage, ItemMapper itemMapper) {
        super(itemStorage, itemMapper, Item.class);
        this.itemMapper = itemMapper;
        this.userStorage = userStorage;
        this.itemStorage = itemStorage;
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        userStorage.read(itemDto.getOwner());
        return super.create(itemDto);
    }

    @Override
    public ItemDto update(Long id, ItemDto itemDto) {
        userStorage.read(itemDto.getOwner());
        checkOwner(id, itemDto.getOwner());
        return super.update(id, itemDto);

    }

    @Override
    public List<ItemDto> readAll(Long userId) {
        List<Item> items = itemStorage.readAll(userId);
        return items.stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.search(query).stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    private void checkOwner(Long itemId, Long ownerId) {
        if (!itemStorage.read(itemId).getOwner().equals(ownerId)) {
            throw new ForbiddenException("Forbidden");
        }
    }
}
