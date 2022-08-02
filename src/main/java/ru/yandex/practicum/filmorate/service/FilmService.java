package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
    }

    public void addLike(Long filmId, Long userId) throws FilmNotFoundException {
        Film film = ((InMemoryFilmStorage) storage).getFilm(filmId);
        if (!film.getLikes().add(userId)) {
            throw new FilmServiceProcessingException(
                    String.format("Like from user %d tp film %s not added.", userId, film.getName()));
        }
        storage.updateFilm(film);
    }

    public void deleteLike(Long filmId, Long userId) throws FilmNotFoundException {
        Film film = ((InMemoryFilmStorage) storage).getFilm(filmId);
        if (!film.getLikes().remove(userId)) {
            throw new FilmServiceProcessingException(
                    String.format("Like from user %d tp film %s not deleted.",
                            userId, film.getName()));
        }
        storage.updateFilm(film);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        return ((InMemoryFilmStorage) storage).getFilms().values().stream()
                .sorted()
                .limit(count)
                .collect(Collectors.toList());
    }
}
