package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Film {
    private Long id;
    @NotBlank
    private final String name;
    private final LocalDate releaseDate;
    @Size(max=200)
    private final String description;
    @Positive
    private final Long duration;
    @NotNull
    private final Mpa mpa;
    @Setter
    private Set<Genre> genres = new LinkedHashSet<>();
}
