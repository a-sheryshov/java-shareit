package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.mapper.UserMapperImpl;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserMapperTest {
    private User user;
    private final UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    public void beforeEach() {
        user = User.builder().id(1L).name("user").email("email@email.com").build();
    }

    @Test
    public void toDtoTest() {
        UserDto dto = userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    public void toObjectTest() {
        UserDto dto = UserDto.builder().id(1L).name("user").email("email@email.com").build();
        User fromDtoUser = userMapper.toObject(dto);
        assertEquals(dto.getId(), fromDtoUser.getId());
        assertEquals(dto.getName(), fromDtoUser.getName());
        assertEquals(dto.getEmail(), fromDtoUser.getEmail());
    }
}