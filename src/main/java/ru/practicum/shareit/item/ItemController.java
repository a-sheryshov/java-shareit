package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entity.AbstractEntityController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.exception.NoUserIdHeaderException;
import ru.practicum.shareit.item.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController extends AbstractEntityController<ItemDto> {
    private static final String INFO_LOG_MSG_RGX = "Request '{}' to '{}', objectId: {}";
    final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        super(itemService);
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), text);
        return itemService.search(text, from, size);
    }

    @GetMapping
    @Override
    public List<ItemDto> readAll(HttpServletRequest request) {
        Long userId = getUserId(request);
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), userId);
        return itemService.readAll(userId, getParameter(request, "from", 0),
                getParameter(request, "size", 10));
    }

    @Override
    @GetMapping("/{id}")
    public ItemDto read(@PathVariable final Long id, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), id);
        Long userId = getUserId(request);
        return itemService.read(id, userId);
    }

    @Override
    @PostMapping
    public ItemDto create(@RequestBody final ItemDto itemDto, HttpServletRequest request) {
        Long userId = getUserId(request);
        return super.create(itemDto.toBuilder().ownerId(userId).build(), request);
    }

    @Override
    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable final Long id, @RequestBody final ItemDto itemDto, HttpServletRequest request) {
        Long userId = getUserId(request);
        return super.update(id, itemDto.toBuilder().ownerId(userId).build(), request);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody CommentDto commentDto, HttpServletRequest request) {
        CommentDto result = itemService.createComment(itemId, userId, commentDto);
        log.info(INFO_LOG_MSG_RGX,
                request.getMethod(), request.getRequestURI(), result.getId());
        return result;

    }

    private Long getUserId(HttpServletRequest request) {
        String userIdHeader = request.getHeader("X-Sharer-User-Id");
        try {
            return Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            throw new NoUserIdHeaderException("X-Sharer-User-Id header is incorrect");
        }
    }

    private Integer getParameter(HttpServletRequest request, String name, int def) {
        String param = request.getParameter(name);
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return def;
        }
    }


}
