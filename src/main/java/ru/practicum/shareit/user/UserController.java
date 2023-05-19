package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.entity.AbstractEntityController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController extends AbstractEntityController<UserDto> {
    final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }
}
