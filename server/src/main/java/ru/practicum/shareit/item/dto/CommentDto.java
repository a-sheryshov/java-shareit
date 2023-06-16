package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class CommentDto extends AbstractEntityDto {

    private String text;

    private String authorName;

    private LocalDateTime created;
}
