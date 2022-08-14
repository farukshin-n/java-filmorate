package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
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

    public Film updateFilm(Film film) throws FilmNotFoundException {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilmList() {
        return filmStorage.getFilmList();
    }

    public Film getFilm(Long id) throws FilmNotFoundException {
        return filmStorage.getFilm(id);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        return filmStorage.getMostLikedFilms(count);
    }
}
