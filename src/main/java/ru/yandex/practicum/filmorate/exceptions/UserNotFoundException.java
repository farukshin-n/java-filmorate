package ru.yandex.practicum.filmorate.exceptions;

import java.io.IOException;

public class UserNotFoundException extends IOException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
