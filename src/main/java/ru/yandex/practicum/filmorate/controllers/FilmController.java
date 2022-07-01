package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Data
@RestController
public class FilmController {
    private long id;
    private final HashMap<Long, Film> films = new HashMap<>();

    @PostMapping(value = "/films")
    public Film create(@RequestBody @Valid Film film) {
        validate(film);

        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Film {} released {} was added with id {}.", film.getName(), film.getReleaseDate(), film.getId());

        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody @Valid Film film) throws FilmNotFoundException {
        validate(film);

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Film {} released {} with id {} was updated.", film.getName(), film.getReleaseDate(), film.getId());
        } else {
            throw new FilmNotFoundException("There wasn't such film in film list.");
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getFilmList() {
        return new ArrayList<>(films.values());
    }

    private long generateId() {
        return id += 1;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Film is older than 28 December 1895.");
        }
    }
}
