package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ProcessingException;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.util.List;

@Data
@Service("filmService")
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("dbFilm") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws SubstanceNotFoundException {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilmList() throws SubstanceNotFoundException {
        return filmStorage.getFilmList();
    }

    public Film getFilm(Long id) throws SubstanceNotFoundException {
        if (id < 0) {
            throw new ValidationException("Id %d is wrong.");
        }
        return filmStorage.getFilm(id);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        if (count == null || count <= 0) {
            throw new ValidationException("Parameter %s mustn't be less or equal zero or null.");
        }

        return filmStorage.getMostLikedFilms(count);
    }
}
