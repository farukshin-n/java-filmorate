package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorageTest {
    private final LikeDbStorage likeDbStorage;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Test
    public void addLikeTest() {
        User testUser1 = new User("login1",
                "email1",
                LocalDate.of(2022, 9, 1));
        testUser1.setName(testUser1.getLogin());

        userDbStorage.createUser(testUser1);

        User testUser2 = new User("login2",
                "email2",
                LocalDate.of(2022, 8, 1));
        testUser2.setName(testUser2.getLogin());

        userDbStorage.createUser(testUser2);

        Mpa testMpa = new Mpa(1L, "mpaName");

        Film filmForTest = new Film(
                "testFilm",
                LocalDate.of(2020,8,10),
                "description",
                120L,
                testMpa
        );
        filmDbStorage.createFilm(filmForTest);

        Optional<Like> testLike = Optional.of(likeDbStorage.addLike(1L, 2L));

        assertThat(testLike)
                .isPresent()
                .hasValueSatisfying(like ->
                        assertThat(like).hasFieldOrPropertyWithValue("filmId", 1L)
                                .hasFieldOrPropertyWithValue("userId", 2L)
                );
    }
}
