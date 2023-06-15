package ru.practicum.shareit.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationError {
    private int statusCode;
    private String error;

}
