package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Data
@RestController
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/mpa")
    public List<Mpa> handleGetMpaList() {
        return mpaService.getMpaList();
    }

    @GetMapping("/mpa/{id}")
    public Mpa handleGetMpa(@PathVariable Long id) throws SubstanceNotFoundException {
        return mpaService.getMpa(id);
    }
}
