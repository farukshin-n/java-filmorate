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
    private long id = 0L;
    @NotBlank
    private final String login;
    @NonNull
    private String name;
    @Email @NotBlank
    private final String email;
    private final LocalDate birthday;
}
