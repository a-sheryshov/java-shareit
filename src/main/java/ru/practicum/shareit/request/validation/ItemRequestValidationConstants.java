package ru.practicum.shareit.request.validation;

public class ItemRequestValidationConstants {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
}
