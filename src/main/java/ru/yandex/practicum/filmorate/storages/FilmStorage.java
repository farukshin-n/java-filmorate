package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    public Film createFilm(Film film);

    public void deleteFilm(Film film);

    public Film updateFilm(Film film) throws FilmNotFoundException;

    public Film getFilm(Long id) throws FilmNotFoundException;

    public Map<Long, Film> getFilms();

    public List<Film> getFilmList();
}
