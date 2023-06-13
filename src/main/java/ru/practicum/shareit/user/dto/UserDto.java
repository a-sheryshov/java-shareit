package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.dto.AbstractEntityDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
public class UserDto extends AbstractEntityDto {
    public static final int MAX_NAME_LEN = 50;
    public static final int MAX_EMAIL_LEN = 50;
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";
    public static final String EMAIL_LEN_VALIDATION_ERR_MSG =
            "Email should be less than " + MAX_EMAIL_LEN + " symbols";
    public static final String EMAIL_FORMAT_ERR_MESSAGE = "Wrong email format";
    public static final String NO_EMAIL_ERR_MESSAGE = "Email is mandatory";
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @Size(max = MAX_EMAIL_LEN, message = EMAIL_LEN_VALIDATION_ERR_MSG)
    @Email(message = EMAIL_FORMAT_ERR_MESSAGE)
    private String email;
}
