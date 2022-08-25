package ru.yandex.practicum.filmorate.exceptions;

import java.io.IOException;

public class SubstanceNotFoundException extends RuntimeException {
    public SubstanceNotFoundException(String s) {
        super(s);
    }
}
