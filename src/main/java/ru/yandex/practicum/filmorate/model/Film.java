package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable<Film> {
    private Long id;
    @NotBlank
    private final String name;
    @Size(max=200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive
    private final Long duration;
    private final Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        if (likes.size() > o.likes.size()) {
            return -1;
        } else if (likes.size() == o.likes.size()) {
            return 0;
        } else {
            return 1;
        }
    }
}
