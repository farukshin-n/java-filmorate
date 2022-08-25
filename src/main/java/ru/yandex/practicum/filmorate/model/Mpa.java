package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mpa {
    private Long id;
    private String name;
}
