package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;

    @Test
    public void FilmDbStorageTest() {
        Mpa testMpa = new Mpa(1l, "mpaName");

        Film filmForTest = new Film(
                "testFilm",
                LocalDate.of(2020,8,10),
                "description",
                120L,
                testMpa
        );

        Optional<Film> testFilm = Optional.of(filmDbStorage.createFilm(filmForTest));

        assertThat(testFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "testFilm")
                );

        Optional<Film> testGetFilm = Optional.of(filmDbStorage.getFilm(1L));

        assertThat(testGetFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "testFilm")
                );

        Film filmForUpdateTest = new Film(
                "film",
                LocalDate.of(2020,8,10),
                "description",
                120L,
                testMpa
        );

        Optional<Film> testUpdateFilm = Optional.of(filmDbStorage.createFilm(filmForUpdateTest));

        assertThat(testUpdateFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "film")
                );
    }
}
