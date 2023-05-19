package ru.practicum.shareit.item.exception;

public class NoUserIdHeaderException extends RuntimeException {
    public NoUserIdHeaderException(String message) {
        super(message);
    }
}
