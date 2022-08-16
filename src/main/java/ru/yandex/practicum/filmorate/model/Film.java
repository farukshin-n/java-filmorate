package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Film {
    private Long id;
    @NotBlank
    private final String name;
    @Size(max=200)
    private final String description;
    @NotNull
    private String ratingMPA;
    private final LocalDate releaseDate;
    @Positive
    private final Long duration;
    private Set<Genre> genre = new HashSet<>();

    public Film(String name, String description, String ratingMPA, LocalDate releaseDate, Long duration) {
        this.name = name;
        this.description = description;
        this.ratingMPA = ratingMPA;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
