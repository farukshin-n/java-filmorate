package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody final Film film) {
        log.info("wtf mpa id {}", film.getMpa().getId());
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody final Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilmList() {
        return filmService.getFilmList();
    }

    @GetMapping("/films/{id}")
    public Film handleGetFilm(@PathVariable Long id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/films/popular")
    public List<Film> handleGetMostLikedFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getMostLikedFilms(count);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void handleAddLike(@PathVariable final Long id, @PathVariable final Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void handleDeleteLike(@PathVariable final Long id, @PathVariable final Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/mpa")
    public List<Mpa> handleGetMpaList() {
        return filmService.getMpaList();
    }

    @GetMapping("/mpa/{id}")
    public Mpa handleGetMpa(@PathVariable final Long id) throws SubstanceNotFoundException {
        return filmService.getMpa(id);
    }

    @GetMapping("/genres")
    public List<Genre> handleGetGenreList() {
        return filmService.getGenreList();
    }

    @GetMapping("/genres/{id}")
    public Genre handleGetGenre(@PathVariable final 
                                    Long id) {
        return filmService.getGenre(id);
    }
}
