package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class UserDto extends AbstractEntityDto {

    private String name;

    private String email;
}
