package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

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
    public Film create(@RequestBody Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Film name is blank.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Film description is longer than 200 characters.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Film is older than 28 December 1895.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Film duration is not positive");
        }

        if (films.containsValue(film)) {
            throw new ValidationException("Such film is already existed.");
        } else {
            film.setId(generateId());
            films.put(film.getId(), film);
            log.info("Film {} released {} was added with id {}.", film.getName(), film.getReleaseDate(), film.getId());
        }
        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody Film film) throws FilmNotFoundException {
        if (film.getName().isBlank()) {
            throw new ValidationException("Film name is null.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Film description is longer than 200 characters.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Film is older than 28 December 1895.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Film duration is not positive");
        } else if (films.containsKey(film.getId())) {
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
}
