package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendshipService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
public class UserController {
    private final UserService service;
    private final FriendshipService friendshipService;

    @Autowired
    public UserController(UserService service, FriendshipService friendshipService) {
        this.service = service;
        this.friendshipService = friendshipService;
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws UserNotFoundException {
        return service.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return service.getUserList();
    }

    @GetMapping("/users/{id}")
    public User handleGetUser(@PathVariable @Valid Long id) throws UserNotFoundException {
        return service.getUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void handleAddFriend(@PathVariable Long id,
                                @PathVariable Long friendId) throws UserNotFoundException {
        friendshipService.addFriendship(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void handleDeleteFriend(@PathVariable Long id,
                                   @PathVariable Long friendId) throws UserNotFoundException {
        friendshipService.deleteFriendship(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> handleGetFriendList(@PathVariable Long id) throws UserNotFoundException {
        return friendshipService.getFriendsList(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> handleGetMutualFriendsList(@PathVariable Long id,
                                                      @PathVariable Long otherId) throws UserNotFoundException {
        return friendshipService.getMutualFriendList(id, otherId);
    }
}
