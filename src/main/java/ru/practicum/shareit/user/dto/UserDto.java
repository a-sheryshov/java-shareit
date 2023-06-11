package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static ru.practicum.shareit.user.validation.UserValidationConstants.*;

@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class UserDto extends AbstractEntityDto {
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @Size(max = MAX_EMAIL_LEN, message = EMAIL_LEN_VALIDATION_ERR_MSG)
    @Email(message = EMAIL_FORMAT_ERR_MESSAGE)
    private String email;
}
