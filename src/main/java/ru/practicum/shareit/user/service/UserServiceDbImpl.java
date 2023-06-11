package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Service
public class UserServiceDbImpl extends AbstractEntityServiceImpl<User, UserDto> implements UserService {

    @Autowired
    public UserServiceDbImpl(UserRepository repository, UserMapper mapper) {
        super(repository, mapper, User.class);
    }

    @Override
    public UserDto update(@Valid @Positive Long id, @Valid UserDto userDto) {
        userDto = userDto.toBuilder().id(id).build();
        return super.update(id, userDto);
    }

}
