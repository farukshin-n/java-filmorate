package ru.yandex.practicum.filmorate.exceptions;

import lombok.Value;

public class ValidationException extends RuntimeException {
    public ValidationException(String msg) {
        super(msg);
    }
}
