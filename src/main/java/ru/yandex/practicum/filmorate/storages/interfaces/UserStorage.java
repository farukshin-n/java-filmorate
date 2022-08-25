package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserStorage {

    User createUser(User user);

    void deleteUser(User user);

    User updateUser(User user) throws SubstanceNotFoundException;

    User getUser(Long id) throws SubstanceNotFoundException;

    List<User> getUserList();
}
