package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Data
@RestController
public class UserController {
    private long id = 0L;
    private final HashMap<Long, User> users = new HashMap<>();

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("User email must not be null and must contain @." +
                    " Check it and try again");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Check your login.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("User's birthday is in future. Check it and try again.");
        } else if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("User {} with id {} is added.", user.getLogin(), user.getId());
        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) throws UserNotFoundException {
        if (user.getEmail().isBlank()) {
            throw new ValidationException("User email must not be null and must contain @." +
                    " Check it and try again");
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Check your login.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("User's birthday is in future. Check it and try again.");
        } else if (users.containsKey(user.getId())) {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.info("User {} with email {} is updated.", user.getLogin(), user.getEmail());
        } else {
            throw new UserNotFoundException("There wasn't user with such id in user list.");
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    private long generateId() {
        return id += 1;
    }
}
