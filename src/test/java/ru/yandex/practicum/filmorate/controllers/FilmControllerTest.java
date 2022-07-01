package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.matchers.Null;
import org.springframework.util.Assert;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController controller;

    @BeforeEach
    void init() {
        FilmController controller = new FilmController();
        this.controller = controller;
    }

    @AfterEach
    void clean() {
        this.controller = null;
    }

    @Test
    void create_default() {
        init();
        Film newFilmOne = new Film("Forrest Gump", "runner",
                LocalDate.of(1999, 10, 12), 67L);
        controller.create(newFilmOne);
        Film expectedFilm = new Film( "Forrest Gump", "runner",
                LocalDate.of(1999, 10, 12), 67L);
        expectedFilm.setId(1L);
        Assertions.assertFalse(controller.getFilms().isEmpty());
        Assertions.assertEquals(1, controller.getFilms().size());
        Assertions.assertEquals(expectedFilm, controller.getFilms().get(newFilmOne.getId()));
    }

    @Test
    void create_withOldDate() {
        Film newFilmOne = new Film("Home Alone", "Run, Forrest, Run",
                LocalDate.of(1800, 1, 1), 67L);

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(newFilmOne);
            }
        });
    }

    @Test
    void create_withNegativeDuration() {
        Film newFilmOne = new Film( "Home Alone", "Run, Forrest, run",
                LocalDate.of(1800, 1, 1), -67L);

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(newFilmOne);
            }
        });
    }

    @Test
    void update_default() throws FilmNotFoundException {
        Film newFilmOne = new Film("Home Alone", "comedy",
                LocalDate.of(1998, 1, 1), 67L);
        controller.create(newFilmOne);

        Film newFilmTwo = new Film( "Home Alone – 2", "comedy",
                LocalDate.of(1998, 1, 1), 67L);
        newFilmTwo.setId(1L);

        controller.update(newFilmTwo);

        Assertions.assertFalse(controller.getFilms().isEmpty());
        Assertions.assertEquals(1, controller.getFilms().size());
        Assertions.assertEquals(newFilmTwo, controller.getFilms().get(1L));
    }

    @Test
    void update_withOldDate() {
        Film newFilmOne = new Film( "Home Alone", "comedy",
                LocalDate.of(1998, 1, 1), 67L);
        controller.create(newFilmOne);

        final Film newFilmTwo = new Film( "Home Alone", "comedy",
                LocalDate.of(1800, 1, 1), 67L);

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(newFilmTwo);
            }
        });
    }
}