package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    public User createUser(User user);

    public void deleteUser(User user);

    public User updateUser(User user) throws UserNotFoundException;
}
