package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storages.interfaces.LikeStorage;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class LikeService {
    private final LikeStorage likeStorage;

    @Autowired
    public LikeService(LikeStorage likeStorage) {
        this.likeStorage = likeStorage;
    }

    public Like addLike(Long filmId, Long userId) {
        return likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        likeStorage.deleteLike(filmId, userId);
    }


}
