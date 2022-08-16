package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storages.interfaces.LikeStorage;

@Data
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeStorage likeStorage;
    private final UserService userService;
    private final FilmService filmService;

    public Like addLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        return likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        likeStorage.deleteLike(filmId, userId);
    }

    private void validateId(Long filmId, Long userId) {
        if (userService.getUser(userId) == null) {
            throw new SubstanceNotFoundException(String.format("User is not found.", userId));
        }

        if (filmService.getFilm(filmId) == null) {
            throw new SubstanceNotFoundException(String.format("User is not found.", userId));
        }
    }

}
