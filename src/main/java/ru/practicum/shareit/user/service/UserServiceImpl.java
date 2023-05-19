package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.entity.service.AbstractEntityServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.EmailAlreadyInUseException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Service
public class UserServiceImpl extends AbstractEntityServiceImpl<User, UserDto> implements UserService {
    final UserStorage storage;
    @Autowired
    public UserServiceImpl(UserStorage storage, UserMapper mapper) {
        super(storage, mapper, User.class);
        this.storage = storage;
    }

    @Override
    public UserDto create(@Valid UserDto userDto) {
        throwIfEmailNotUnique(userDto);
        return super.create(userDto);
    }

    @Override
    public UserDto update(@Valid @Positive Long id, @Valid UserDto userDto) {
        userDto = userDto.toBuilder().id(id).build();
        throwIfEmailNotUnique(userDto);
        return super.update(id, userDto);
    }

    private void throwIfEmailNotUnique(UserDto userDtoToCheck) {
        for (User user : storage.readAll()) {
            if (userDtoToCheck.getEmail() == null) {
                return;
            }
            if (userDtoToCheck.getId() != null) {
                if (user.getEmail().equalsIgnoreCase(userDtoToCheck.getEmail())
                        && !user.getId().equals(userDtoToCheck.getId())) {
                    throw new EmailAlreadyInUseException("Email " + userDtoToCheck.getEmail() + " already in use.");
                }
            } else {
                if (user.getEmail().equalsIgnoreCase(userDtoToCheck.getEmail())) {
                    throw new EmailAlreadyInUseException("Email " + userDtoToCheck.getEmail() + " already in use.");
                }
            }
        }
    }
}
