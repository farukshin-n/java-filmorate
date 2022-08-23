package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="id")
public class Like {
    private final Long filmId;
    private final Long userId;
}
