package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Friendship {
    private final Long friendOne;
    private final Long friendTwo;
}
