package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    public static final int MAX_DESCRIPTION_LEN = 200;
    public static final int MAX_NAME_LEN = 50;
    public static final String DESC_LEN_VALIDATION_ERR_MSG =
            "Description should be less than " + MAX_DESCRIPTION_LEN + " symbols";
    public static final String NAME_LEN_VALIDATION_ERR_MSG =
            "Name should be less than " + MAX_NAME_LEN + " symbols";


    @Size(max = MAX_NAME_LEN, message = NAME_LEN_VALIDATION_ERR_MSG)
    private String name;


    @Size(max = MAX_DESCRIPTION_LEN, message = DESC_LEN_VALIDATION_ERR_MSG)
    private String description;

    private Boolean available;

    @Positive
    private Long requestId;
}