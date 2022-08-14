package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Like {
    private final Long likeId;
    private final Long filmId;
    private final Long userId;
}
