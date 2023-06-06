package ru.practicum.shareit.item.validation;

public class ItemValidationConstants {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final int MAX_NAME_LEN = 50;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";
}
