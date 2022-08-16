package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    @NotBlank
    private final String login;
    private String name;
    @Email
    private final String email;
    private final LocalDate birthday;
}
