package ru.yandex.practicum.filmorate.storages;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Data
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{
    private long id;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film createFilm(Film film) {
        validate(film);

        film.setId(generateId());
        films.put(film.getId(), film);
        log.info("Film {} released {} was added with id {}.", film.getName(), film.getReleaseDate(), film.getId());
        return film;
    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFoundException {
        validate(film);

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Film {} released {} with id {} was updated.", film.getName(), film.getReleaseDate(), film.getId());
        } else {
            throw new FilmNotFoundException("There wasn't such film in film list.");
        }

        return film;
    }

    public Film getFilm(Long id) {
        return films.get(id);
    }

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
