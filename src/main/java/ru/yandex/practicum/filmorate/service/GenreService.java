package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;

import java.util.List;

@Data
@Service
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreService(@Qualifier("dbGenre") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }

    public Genre getGenre(Long genreId) {
        return genreStorage.getGenre(genreId);
    }
}