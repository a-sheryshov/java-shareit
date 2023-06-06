package ru.practicum.shareit.user.validation;

public class UserValidationConstants {
    public static final int MAX_NAME_LEN = 50;
    public static final int MAX_EMAIL_LEN = 50;
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";
    public static final String EMAIL_LEN_VALIDATION_ERR_MSG =
            "Email should be less than " + MAX_EMAIL_LEN + " symbols";
    public static final String NAME_VALIDATION_ERR_MESSAGE = "Name is mandatory";
    public static final String EMAIL_FORMAT_ERR_MESSAGE = "Wrong email format";
    public static final String NO_EMAIL_ERR_MESSAGE = "Email is mandatory";
}
