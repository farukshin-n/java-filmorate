package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    public Film createFilm(Film film);

    public void deleteFilm(Film film);

    public Film updateFilm(Film film) throws FilmNotFoundException;
}
