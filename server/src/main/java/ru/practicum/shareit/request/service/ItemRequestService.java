package ru.practicum.shareit.request.service;

import ru.practicum.shareit.entity.service.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService extends Service<ItemRequestDto> {
    List<ItemRequestDto> readAllByUser(Long userId);

    List<ItemRequestDto> readAll(int from, int size, Long userId);

    ItemRequestDto readById(Long requestId, Long userId);
}
