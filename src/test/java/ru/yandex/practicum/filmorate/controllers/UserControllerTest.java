package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController controller;

    @BeforeEach
    void init() {
        UserController controller = new UserController();
        this.controller = controller;
    }

    @AfterEach
    void clean() {
        this.controller = null;
    }

    @Test
    void create_default() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        final User expectedUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        expectedUser.setName("Jackson");

        Assertions.assertFalse(controller.getUsers().isEmpty());
        Assertions.assertEquals(1, controller.getUsers().size());
        Assertions.assertEquals(expectedUser, testUser);
    }

    @Test
    void create_withEmptyEmail() {
        User testUser = new User(1L, "", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(testUser);
            }
        });
    }

    @Test
    void create_withWrongEmail() {
        User testUser = new User(1L, "yandex", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(testUser);
            }
        });
    }

    @Test
    void create_withEmptyLogin() {
        User testUser = new User(1L, "yandex@yandex.ru", "",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(testUser);
            }
        });
    }

    @Test
    void create_withWrongLogin() {
        User testUser = new User(1L, "yandex@yandex.ru", "stone wall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(testUser);
            }
        });
    }

    @Test
    void create_withEmptyName() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));

        controller.create(testUser);

        Assertions.assertEquals(testUser.getLogin(), testUser.getName());
    }

    @Test
    void create_withFutureBirthday() {
        User testUser = new User(1L, "yandex@yandex.ru", "stone wall",
                LocalDate.of(2045, 12,1));
        testUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.create(testUser);
            }
        });
    }

    @Test
    void update_default() throws UserNotFoundException {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex@yandex.ru", "stonewall111",
                LocalDate.of(1845, 12,1));
        updatedUser.setName("Jackson");

        controller.update(updatedUser);

        final User expectedUser = new User(1L, "yandex@yandex.ru", "stonewall111",
                LocalDate.of(1845, 12,1));
        expectedUser.setName("Jackson");

        Assertions.assertFalse(controller.getUsers().isEmpty());
        Assertions.assertEquals(1, controller.getUsers().size());
        Assertions.assertEquals(expectedUser, updatedUser);
    }

    @Test
    void update_withEmptyEmail() throws UserNotFoundException {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "", "stonewall111",
                LocalDate.of(1845, 12,1));
        updatedUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(updatedUser);
            }
        });
    }

    @Test
    void update_withWrongEmail() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex", "stonewall111",
                LocalDate.of(1845, 12,1));
        updatedUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(updatedUser);
            }
        });
    }

    @Test
    void update_withEmptyLogin() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex@yandex.ru", "",
                LocalDate.of(1845, 12,1));
        updatedUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(updatedUser);
            }
        });
    }

    @Test
    void update_withWrongLogin() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex@yandex.ru", "stone wall 111",
                LocalDate.of(1845, 12,1));
        updatedUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(updatedUser);
            }
        });
    }

    @Test
    void update_withEmptyName() throws UserNotFoundException {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex@yandex.ru", "stonewall111",
                LocalDate.of(1845, 12,1));
        controller.update(updatedUser);

        Assertions.assertEquals(updatedUser.getLogin(), updatedUser.getName());
    }

    @Test
    void update_withBirthdayInFuture() {
        User testUser = new User(1L, "yandex@yandex.ru", "stonewall",
                LocalDate.of(1845, 12,1));
        testUser.setName("Jackson");

        controller.create(testUser);

        User updatedUser = new User(1L, "yandex@yandex.ru", "stonewall111",
                LocalDate.of(2045, 12,1));
        updatedUser.setName("Jackson");

        Assertions.assertThrows(ValidationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.update(updatedUser);
            }
        });
    }
}