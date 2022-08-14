package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class FilmService {
    private final FilmStorage storage;

    @Autowired
    public FilmService(@Qualifier("dbFilm") FilmStorage storage) {
        this.storage = storage;
    }

    public Film createFilm(Film film) {
        return storage.createFilm(film);
    }

    public Film updateFilm(Film film) throws FilmNotFoundException {
        return storage.updateFilm(film);
    }

    public List<Film> getFilmList() {
        return storage.getFilmList();
    }

    public Film getFilm(Long id) throws FilmNotFoundException {
        return storage.getFilm(id);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        return storage.getMostLikedFilms(count);
    }
}
