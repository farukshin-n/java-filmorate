package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    private final long id;
    @NotNull @Email
    private final String email;
    @NotNull
    private final String login;
    public String name;
    @NotNull
    private final LocalDate birthday;





}
