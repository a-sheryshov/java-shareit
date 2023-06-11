package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.entity.exception.ObjectNotFoundException;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.CommentCreationException;
import ru.practicum.shareit.item.exception.ForbiddenException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.booking.BookingStatus.APPROVED;

@Service
@Slf4j
@Validated
public class ItemServiceImpl extends AbstractEntityServiceImpl<Item, ItemDto> implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final BookingMapper bookingMapper;
    private final CommentMapper commentMapper;
    private final Sort sortDesc = Sort.by(Sort.Direction.DESC, "start");
    private final Sort sortAsc = Sort.by(Sort.Direction.ASC, "start");
    private final Sort sortIdAsc = Sort.by(Sort.Direction.ASC, "id");

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository,
                           BookingRepository bookingRepository, CommentRepository commentRepository,
                           ItemMapper itemMapper, BookingMapper bookingMapper, CommentMapper commentMapper) {
        super(itemRepository, itemMapper, Item.class);
        this.itemMapper = itemMapper;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
        this.bookingMapper = bookingMapper;
        this.commentMapper = commentMapper;
    }

    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto) {
        Item item = itemMapper.toObject(itemDto);
        User user = userRepository.findById(itemDto.getOwnerId()).orElseThrow(
                () -> new ObjectNotFoundException("User " + itemDto.getOwnerId() + " not found"));
        item.setOwner(user);
        itemRepository.save(item);
        log.info("{} added: {}", Item.class.getSimpleName(), item.getId());
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDto update(Long id, ItemDto itemDto) {
        itemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item " + id + " not found"));
        checkOwner(id, itemDto.getOwnerId());
        return super.update(id, itemDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> readAll(Long userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId, sortIdAsc);
        List<ItemDto> itemDtoList = items.stream().map(itemMapper::toDto).collect(Collectors.toList());
        itemDtoList.forEach(itemDto -> {
            List<Booking> lastBooking = bookingRepository.findAllByItemIdAndStartBeforeAndStatusNot(itemDto.getId(),
                    LocalDateTime.now(), BookingStatus.REJECTED, sortDesc);
            itemDto.setLastBooking(lastBooking.isEmpty() ?
                    null : bookingMapper.toDtoShort(lastBooking.get(0)));
            List<Booking> nextBooking = bookingRepository.findAllByItemIdAndStartAfterAndStatusNot(itemDto.getId(),
                    LocalDateTime.now(), BookingStatus.REJECTED, sortAsc);
            itemDto.setNextBooking(nextBooking.isEmpty() ?
                    null : bookingMapper.toDtoShort(nextBooking.get(0)));
            itemDto.setComments(commentRepository.findAllByItemId(itemDto.getId())
                    .stream().map(commentMapper::toDto).collect(Collectors.toList()));
        });
        return itemDtoList;
    }

    //@Transactional(readOnly = true)
    @Override
    public ItemDto read(Long id, Long ownerId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item " + id + " not found"));
        ItemDto itemDto = itemMapper.toDto(item);
        itemDto.setComments(commentRepository.findAllByItemId(id)
                .stream().map(commentMapper::toDto).collect(Collectors.toList()));
        if (item.getOwner().getId().equals(ownerId)) {
            List<Booking> lastBooking = bookingRepository.findAllByItemIdAndStartBeforeAndStatusNot(itemDto.getId(),
                    LocalDateTime.now(), BookingStatus.REJECTED, sortDesc);
            itemDto.setLastBooking(lastBooking.isEmpty() ?
                    null : bookingMapper.toDtoShort(lastBooking.get(0)));
            List<Booking> nextBooking = bookingRepository.findAllByItemIdAndStartAfterAndStatusNot(itemDto.getId(),
                    LocalDateTime.now(), BookingStatus.REJECTED, sortAsc);
            itemDto.setNextBooking(nextBooking.isEmpty() ?
                    null : bookingMapper.toDtoShort(nextBooking.get(0)));
        }
        return itemDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> search(String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.findAllByNameOrDescriptionContainingIgnoreCaseAndAvailableTrue(query, query)
                .stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    private void checkOwner(Long itemId, Long ownerId) {
        if (!itemRepository.findById(itemId).orElseThrow(() -> new ObjectNotFoundException("Item " + itemId +
                        " not found"))
                .getOwner().getId().equals(ownerId)) {
            throw new ForbiddenException("Forbidden");
        }
    }

    @Transactional
    @Override
    public CommentDto createComment(Long itemId, Long userId, CommentDto commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User " + userId + " not found"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ObjectNotFoundException("Item " + itemId + " not found"));
        if (bookingRepository.findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(userId, itemId, APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new CommentCreationException("Not existing/not ended booking");
        }
        Comment comment = commentMapper.toObject(commentDto);
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

}
