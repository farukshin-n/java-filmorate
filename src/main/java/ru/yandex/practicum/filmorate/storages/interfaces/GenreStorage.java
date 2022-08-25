package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    List<Genre> getGenreList();

    Genre getGenre(Long genreId);

    void setFilmGenreList(Film film);

    Film updateFilmGenreList(Film film);
}
