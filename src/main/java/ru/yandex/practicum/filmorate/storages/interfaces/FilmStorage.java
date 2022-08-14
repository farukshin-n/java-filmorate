package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    public Film createFilm(Film film);

    public void deleteFilm(Film film);

    public Film updateFilm(Film film) throws FilmNotFoundException;

    public Film getFilm(Long id) throws FilmNotFoundException;

    public List<Film> getFilmList();

    public List<Film> getMostLikedFilms(Integer count);
}
