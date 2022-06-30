package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    private Long id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Long duration;
}
