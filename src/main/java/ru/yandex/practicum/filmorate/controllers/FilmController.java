package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.storages.interfaces.LikeStorage;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
public class FilmController {
    private final FilmService service;
    private final LikeService likeService;

    @Autowired
    public FilmController(FilmService service, LikeService likeService) {
        this.service = service;
        this.likeService = likeService;
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        return service.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film film) throws FilmNotFoundException {
        return service.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getFilmList() {
        return service.getFilmList();
    }

    @GetMapping("/films/{id}")
    public Film handleGetFilm(@PathVariable Long id) throws FilmNotFoundException {
        return service.getFilm(id);
    }

    @GetMapping("/films/popular?count={count}")
    public List<Film> handleGetMostLikedFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return service.getMostLikedFilms(count);
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
