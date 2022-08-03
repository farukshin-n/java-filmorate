package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Data
@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return service.getStorage().createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws UserNotFoundException {
        return service.getStorage().updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return service.getStorage().getUserList();
    }

    @GetMapping("/users/{id}")
    public User handleGetUser(@PathVariable Long id) throws UserNotFoundException {
        return service.getStorage().getUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void handleAddFriend(@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void handleDeleteFriend(@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        service.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public ArrayList<User> handleGetFriendList(@PathVariable Long id) throws UserNotFoundException {
        return service.getFriendList(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public ArrayList<User> handleGetMutualFriendsList(@PathVariable Long id, @PathVariable Long otherId) throws UserNotFoundException {
        return service.getMutualFriendsList(id, otherId);
    }
}
