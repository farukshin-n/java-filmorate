package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User createUser(User user);

    public void deleteUser(User user);

    public User updateUser(User user) throws UserNotFoundException;

    public User getUser(Long id) throws UserNotFoundException;

    public List<User> getUserList();
}
