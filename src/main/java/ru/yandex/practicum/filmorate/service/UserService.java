package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendProcessingException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.util.List;

@Data
@Service("userService")
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("dbUser") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUser(Long id) throws UserNotFoundException {
        final User user = userStorage.getUser(id);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    public User createUser(User user) throws UserNotFoundException {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws UserNotFoundException {
        final User resultUser = userStorage.getUser(user.getId());

        if (resultUser == null) {
            throw new UserNotFoundException(String.format("User %s not found in database.", user.getId()));
        }

        return userStorage.updateUser(user);
    }

    public List<User> getUserList() {
        return userStorage.getUserList();
    }
}
