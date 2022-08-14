package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    public List<Mpa> getMpaList();

    public Mpa getMpa(Long mpaId);
}
