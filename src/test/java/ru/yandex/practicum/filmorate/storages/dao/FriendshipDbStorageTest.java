package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Friendship;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendshipDbStorageTest {
    private final FriendshipDbStorage friendshipDbStorage;

    @Test
    public void addFriendshipTest() {
        Optional<Friendship> testFriendship = Optional.of(friendshipDbStorage.addFriendship(1L, 2L));

        assertThat(testFriendship)
                .isPresent()
                .hasValueSatisfying(fr ->
                        assertThat(fr).hasFieldOrPropertyWithValue("friendOne", 1L)
                                .hasFieldOrPropertyWithValue("friendTwo", 2L)
                );
    }
}
