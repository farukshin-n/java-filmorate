package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FriendshipDbStorageTest {
    private final FriendshipDbStorage friendshipDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    public void addFriendshipTest() {
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

        Optional<Friendship> testFriendship = Optional.of(friendshipDbStorage.addFriendship(1L, 2L));

        assertThat(testFriendship)
                .isPresent()
                .hasValueSatisfying(fr ->
                        assertThat(fr).hasFieldOrPropertyWithValue("friendOne", 1L)
                                .hasFieldOrPropertyWithValue("friendTwo", 2L)
                );
    }
}
