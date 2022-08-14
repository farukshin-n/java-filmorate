package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
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
