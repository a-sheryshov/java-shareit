package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemRequestServiceImpl extends AbstractEntityServiceImpl<ItemRequest, ItemRequestDto>
        implements ItemRequestService {
    ItemRequestRepository itemRequestRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;
    ItemRequestMapper itemRequestMapper;
    ItemMapper itemMapper;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository,
                                   UserRepository userRepository, ItemRepository itemRepository,
                                   ItemRequestMapper itemRequestMapper, ItemMapper itemMapper) {
        super(itemRequestRepository, itemRequestMapper, ItemRequest.class);
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemRequestMapper = itemRequestMapper;
        this.itemMapper = itemMapper;
    }

    @Transactional
    @Override
    public ItemRequestDto create(ItemRequestDto dto) {
        User user = userRepository.findById(dto.getRequestorId()).orElseThrow(
                () -> new ObjectNotFoundException("User " + dto.getRequestorId() + " not found"));
        ItemRequest itemRequest = itemRequestMapper.toObject(dto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setRequestor(user);
        itemRequestRepository.save(itemRequest);
        log.info("{} added: {}", ItemRequest.class.getSimpleName(), itemRequest.getId());
        return itemRequestMapper.toDto(itemRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> readAllByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User " + userId + " not found"));
        List<ItemRequestDto> itemRequestDtos = itemRequestRepository.findAllByRequestorIdOrderByCreatedAsc(userId)
                .stream()
                .map(itemRequestMapper::toDto)
                .collect(Collectors.toList());
        itemRequestDtos.forEach(this::setItemsToItemRequestDto);
        return itemRequestDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequestDto> readAll(int from, int size, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User " + userId + " not found"));
        List<ItemRequestDto> itemRequestDtos = itemRequestRepository.findAllByRequestorNotLikeOrderByCreatedAsc(user,
                        PageRequest.of(from, size))
                .stream()
                .map(itemRequestMapper::toDto)
                .collect(Collectors.toList());
        itemRequestDtos.forEach(this::setItemsToItemRequestDto);
        return itemRequestDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public ItemRequestDto readById(Long requestId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User " + userId + " not found"));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("Item request " + requestId + " not found"));
        ItemRequestDto itemRequestDto = itemRequestMapper.toDto(itemRequest);
        setItemsToItemRequestDto(itemRequestDto);
        return itemRequestDto;
    }

    private void setItemsToItemRequestDto(ItemRequestDto itemRequestDto) {
        itemRequestDto.setItems(itemRepository.findAllByRequestId(itemRequestDto.getId())
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList()));
    }
}
