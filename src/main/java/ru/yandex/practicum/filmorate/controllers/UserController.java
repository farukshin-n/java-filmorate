package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.util.List;

@Data
@RestController
public class UserController {
    private final UserStorage storage;
    private final UserService service;

    @Autowired
    public UserController(UserStorage storage, UserService service) {
        this.storage = storage;
        this.service = service;
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return storage.createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws UserNotFoundException {
        return storage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return ((InMemoryUserStorage) storage).getUserList();
    }
}
