package ru.practicum.shareit.user.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}
