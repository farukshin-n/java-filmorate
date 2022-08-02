package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(InMemoryFilmStorage storage) {
        this.storage = storage;
    }

    public void addLike(Film film, User user) throws FilmNotFoundException {
        if (!film.getLikes().add(user.getId())) {
            throw new FilmServiceProcessingException(
                    String.format("Like from user %l tp film %s not added.",
                    user.getId(), film.getName()));
        }
        storage.updateFilm(film);
    }

    public void deleteLike(Film film, User user) throws FilmNotFoundException {
        if (!film.getLikes().remove(user.getId())) {
            throw new FilmServiceProcessingException(
                    String.format("Like from user %l tp film %s not deleted.",
                            user.getId(), film.getName()));
        }
        storage.updateFilm(film);
    }

    public List<Film> getTenMostLikedFilms() {
        return ((InMemoryFilmStorage) storage).getFilms().values().stream()
                .sorted()
                .limit(10)
                .collect(Collectors.toList());
    }
}
