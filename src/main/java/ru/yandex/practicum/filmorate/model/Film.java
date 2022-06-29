package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    @NotNull
    private final Long id;
    @NotNull
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Long duration;
}
