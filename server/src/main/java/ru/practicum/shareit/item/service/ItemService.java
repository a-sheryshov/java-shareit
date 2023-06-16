package ru.practicum.shareit.item.service;

import ru.practicum.shareit.entity.service.Service;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService extends Service<ItemDto> {

    List<ItemDto> search(String query, int from, int size);

    List<ItemDto> readAll(Long userId,  int from, int size);

    ItemDto read(Long itemId, Long userId);

    CommentDto createComment(Long itemId, Long userId, CommentDto commentDto);
}
