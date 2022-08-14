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
@Service
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService(@Qualifier("dbUser") UserStorage storage) {
        this.storage = storage;
    }

    public User getUser(Long id) throws UserNotFoundException {
        return storage.getUser(id);
    }

    public User createUser(User user) {
        return storage.createUser(user);
    }

    public User updateUser(User user) throws UserNotFoundException {
        return storage.updateUser(user);
    }

    public List<User> getUserList() {
        return storage.getUserList();
    }
}
