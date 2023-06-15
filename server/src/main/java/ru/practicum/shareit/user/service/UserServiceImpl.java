package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
public class UserServiceImpl extends AbstractEntityServiceImpl<User, UserDto> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper, User.class);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        userDto = userDto.toBuilder().id(id).build();
        return super.update(id, userDto);
    }

}
