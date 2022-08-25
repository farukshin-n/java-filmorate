package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Data
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public User create(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody @Valid User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/users/{id}")
    public User handleGetUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void handleAddFriend(@PathVariable Long id,
                                @PathVariable Long friendId) {
        userService.addFriendship(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void handleDeleteFriend(@PathVariable Long id,
                                   @PathVariable Long friendId) {
        userService.deleteFriendship(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> handleGetFriendList(@PathVariable Long id) {
        return userService.getFriendsList(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> handleGetMutualFriendsList(@PathVariable Long id,
                                                      @PathVariable Long otherId) {
        return userService.getMutualFriendList(id, otherId);
    }
}
