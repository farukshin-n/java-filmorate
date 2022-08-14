package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface LikeStorage {
    public Like addLike(Long filmId, Long userId);

    public void deleteLike(Long filmId, Long userId);
}
