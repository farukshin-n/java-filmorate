package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.LikeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final LikeService likeService;

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film film) {
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

    @GetMapping("/films/popular?count={count}")
    public List<Film> handleGetMostLikedFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getMostLikedFilms(count);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void handleAddLike(@PathVariable Long id, @PathVariable Long userId) {
        likeService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void handleDeleteLike(@PathVariable Long id, @PathVariable Long userId) {
        likeService.deleteLike(id, userId);
    }
}
