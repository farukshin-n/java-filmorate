package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.LikeStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.MpaStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    final LocalDate CHECK_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(@Qualifier("dbFilm") FilmStorage filmStorage,
                       MpaStorage mpaStorage,
                       GenreStorage genreStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    public Film createFilm(Film film) {
        validateFilmByDate(film);
        if (film.getGenres() != null) {
            genreStorage.updateFilmGenreList(film);
        }
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        if (film.getId() <= 0 || !filmStorage.getFilmList().contains(film)) {
            throw new SubstanceNotFoundException(String.format("Film with id %d not found", film.getId()));
        }
        validateFilmByDate(film);

        if (film.getGenres() != null) {
            genreStorage.updateFilmGenreList(film);
        }

        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilmList() {
        return filmStorage.getFilmList();
    }

    public Film getFilm(Long id) {
        if (id < 0) {
            throw new SubstanceNotFoundException(String.format("Not found film with such id %d.", id));
        }
        Film resultFilm = filmStorage.getFilm(id);

        genreStorage.setFilmGenreList(resultFilm);

        return resultFilm;
    }

    private void validateFilmByDate(Film film) {
        if (film.getReleaseDate().isBefore(CHECK_DATE)) {
            throw new ValidationException("Film is older than 28 December 1895.");
        }
    }

    public List<Film> getMostLikedFilms(Integer count) {
        if (count == null || count <= 0) {
            throw new ValidationException("Parameter %s mustn't be less or equal zero or null.");
        }

        return filmStorage.getMostLikedFilms(count);
    }

    public List<Mpa> getMpaList() {
        return mpaStorage.getMpaList();
    }

    public Mpa getMpa(Long mpaId) throws SubstanceNotFoundException {
        if (mpaId < 0) {
            throw new SubstanceNotFoundException(String.format("Mpa with id %d isn't exist.", mpaId));
        }
        Mpa resultMpa = mpaStorage.getMpaById(mpaId);

        if (resultMpa == null) {
            throw new SubstanceNotFoundException(String.format("Mpa not found in database.", mpaId));
        }

        return resultMpa;
    }

    public Like addLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        return likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        likeStorage.deleteLike(filmId, userId);
    }

    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }

    public Genre getGenre(Long genreId) {
        if (genreId == null || genreId < 0) {
            throw new SubstanceNotFoundException(String.format("Id %d is less than zero or null.", genreId));
        }
        return genreStorage.getGenre(genreId);
    }

    private void validateId(Long filmId, Long userId) {
        if (userId <= 0) {
            throw new SubstanceNotFoundException(String.format("User is not found.", userId));
        }

        if (filmId <= 0) {
            throw new SubstanceNotFoundException(String.format("User is not found.", userId));
        }
    }
}
