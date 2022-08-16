package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorageTest {
    private final LikeDbStorage likeDbStorage;

    @Test
    public void addLikeTest() {
        Optional<Like> testLike = Optional.of(likeDbStorage.addLike(1L, 2L));

        assertThat(testLike)
                .isPresent()
                .hasValueSatisfying(like ->
                        assertThat(like).hasFieldOrPropertyWithValue("filmId", 1L)
                                .hasFieldOrPropertyWithValue("userId", 2L)
                );
    }
}
