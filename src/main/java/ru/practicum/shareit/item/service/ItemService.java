package ru.practicum.shareit.item.service;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.entity.service.Service;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface ItemService extends Service<ItemDto> {

    List<ItemDto> search(String query, @Min(value = 0, message = "Should be greater than 0") int from,
                         @Positive int size);

    List<ItemDto> readAll(@Positive Long userId, @Min(value = 0, message = "Should be greater than 0") int from,
                          @Positive int size);

    ItemDto read(@Positive Long itemId, @Positive Long userId);

    CommentDto createComment(@Positive Long itemId, @Positive Long userId, @NotNull @Valid CommentDto commentDto);
}
