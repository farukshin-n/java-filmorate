package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Film {
    private final Long id;
    @NotBlank
    private final String name;
    @Size(max=200)
    private final String description;
    private List<Genre> genre;
    private final Long ratingMPA;
    private final LocalDate releaseDate;
    @Positive
    private final Long duration;


}
