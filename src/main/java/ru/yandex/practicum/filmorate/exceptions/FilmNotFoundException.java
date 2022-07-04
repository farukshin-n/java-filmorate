package ru.yandex.practicum.filmorate.exceptions;

import java.io.IOException;

public class FilmNotFoundException extends IOException {
    public FilmNotFoundException(String s) {
        super(s);
    }
}
