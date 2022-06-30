package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    private long id = 0L;
    private final String email;
    private final String login;
    @NonNull
    private String name;
    private final LocalDate birthday;
}
