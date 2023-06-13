package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.practicum.shareit.entity.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends AbstractEntity {
    public static final int MAX_NAME_LEN = 50;
    public static final int MAX_EMAIL_LEN = 50;
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";
    public static final String EMAIL_LEN_VALIDATION_ERR_MSG =
            "Email should be less than " + MAX_EMAIL_LEN + " symbols";
    public static final String NAME_VALIDATION_ERR_MESSAGE = "Name is mandatory";
    public static final String EMAIL_FORMAT_ERR_MESSAGE = "Wrong email format";
    public static final String NO_EMAIL_ERR_MESSAGE = "Email is mandatory";
    @Column(length = MAX_NAME_LEN, nullable = false)
    @NotBlank(message = NAME_VALIDATION_ERR_MESSAGE)
    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;

    @Column(unique = true, length = MAX_EMAIL_LEN, nullable = false)
    @NotBlank(message = NO_EMAIL_ERR_MESSAGE)
    @Email(message = EMAIL_FORMAT_ERR_MESSAGE)
    @Size(max = MAX_EMAIL_LEN, message = EMAIL_LEN_VALIDATION_ERR_MSG)
    private String email;
}
