package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.entity.AbstractEntityController;
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
    public ItemController(ItemService itemService){
        super(itemService);
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text, HttpServletRequest request) {
        log.info(INFO_LOG_MSG_RGX ,
                request.getMethod(), request.getRequestURI(), "N/A");
        return itemService.search(text);
    }
    @Override
    @GetMapping
    public List<ItemDto> readAll(HttpServletRequest request) {
        Long userId = getUserId(request);
        log.info(INFO_LOG_MSG_RGX ,
                request.getMethod(), request.getRequestURI(), userId);
        return itemService.readAll(userId);
    }
    @Override
    @PostMapping
    public ItemDto create(@RequestBody final ItemDto itemDto, HttpServletRequest request) {
        Long userId = getUserId(request);
        return super.create(itemDto.toBuilder().owner(userId).build(), request);
    }
    @Override
    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable final Long id, @RequestBody final ItemDto itemDto
            , HttpServletRequest request) {
        Long userId = getUserId(request);
        return super.update(id, itemDto.toBuilder().owner(userId).build(), request);
    }

    private Long getUserId(HttpServletRequest request){
        String userIdHeader = request.getHeader("X-Sharer-User-Id");
        if (!(userIdHeader != null && userIdHeader.isBlank())){
            return Long.parseLong(userIdHeader);
        } else {
            throw new NoUserIdHeaderException("X-Sharer-User-Id header is mandatory");
        }
    }

}
