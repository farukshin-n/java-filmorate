package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    public List<Genre> getGenreList();

    public Genre getGenre(Long genreId);
}
