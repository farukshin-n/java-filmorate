package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FilmController {
    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        return service.getStorage().createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film film) throws FilmNotFoundException {
        return service.getStorage().updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilmList() {
        return service.getStorage().getFilmList();
    }

    @GetMapping("/films/{id}")
    public Film handleGetFilm(@PathVariable Long id) {
        return service.getStorage().getFilm(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void handleAddLike(@PathVariable Long id, @PathVariable Long userId) throws FilmNotFoundException {
        service.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void handleDeleteLike(@PathVariable Long id, @PathVariable Long userId) throws FilmNotFoundException {
        service.deleteLike(id, userId);
    }

    @GetMapping("/films/popular?count={count}")
    public List<Film> handleGetMostLikedFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return service.getMostLikedFilms(count);
    }
}
