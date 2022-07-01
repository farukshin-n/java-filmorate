package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private long id = 0L;
    @Email @NotBlank
    private final String email;
    @NotBlank
    private final String login;
    @NonNull
    private String name;
    private final LocalDate birthday;
}
