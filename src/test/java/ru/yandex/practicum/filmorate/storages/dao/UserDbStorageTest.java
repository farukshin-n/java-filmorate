package ru.yandex.practicum.filmorate.storages.dao;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserDbStorage userDbStorage;

    @Test
    public void setUserDbStorageTest() {
        User testUser = new User("login",
                "email",
                LocalDate.of(2022, 9, 01));

        testUser.setName(testUser.getLogin());

        Optional<User> testUserOptional = Optional.of(userDbStorage.createUser(testUser));

        assertThat(testUserOptional)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", 1L));

        Optional<User> userOptional = Optional.of(userDbStorage.getUser(1L));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );

        User testUpdateUser = new User("login_new",
                "email",
                LocalDate.of(2022, 9, 01));
        testUpdateUser.setName(testUpdateUser.getLogin());
        testUpdateUser.setId(1L);

        Optional<User> testUserUpdateOptional = Optional.of(userDbStorage.updateUser(testUpdateUser));

        assertThat(testUserUpdateOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "login_new")
                );
    }
}
