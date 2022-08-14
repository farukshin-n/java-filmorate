package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Data
@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<Genre> handleGetGenreList() {
        return genreService.getGenreList();
    }

    @GetMapping("/genres/{id}")
    public Genre handleGetGenre(@PathVariable Long id) {
        return genreService.getGenre(id);
    }
}
