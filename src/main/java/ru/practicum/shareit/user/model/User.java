package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.practicum.shareit.user.validation.UserValidationConstants.*;

@Data
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends AbstractEntity {
    @NotBlank(message = NAME_VALIDATION_ERR_MESSAGE)
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;
    @NotBlank(message = NO_EMAIL_ERR_MESSAGE)
    @Email(message = EMAIL_FORMAT_ERR_MESSAGE)
    private String email;
}
