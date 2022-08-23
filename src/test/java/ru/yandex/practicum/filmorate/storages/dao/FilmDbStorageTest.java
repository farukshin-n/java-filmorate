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

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;

    @Test
    public void getFilmTest() throws SubstanceNotFoundException {
        Optional<Film> testFilm = Optional.of(filmDbStorage.getFilm(2L));

        assertThat(testFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 2L)
                );
    }

    @Test
    public void updateFilmTest() throws SubstanceNotFoundException {
        Genre genre1 = new Genre(5L, "horror");
        Genre genre2 = new Genre(6L, "узшс");

        Set<Genre> testGenre = new HashSet<>();
        testGenre.add(genre1);
        testGenre.add(genre2);

        Film testFilm = new Film("krik",
                LocalDate.of(2019,6,30),
                "incredible film!",
                120L);

        Optional<Film> resultFilm = Optional.of(filmDbStorage.updateFilm(testFilm));

        assertThat(resultFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("ratingMPA", "G").
                        hasFieldOrPropertyWithValue("description", "incredible film!")
                                .hasFieldOrPropertyWithValue("genre", testGenre));
    }
}
