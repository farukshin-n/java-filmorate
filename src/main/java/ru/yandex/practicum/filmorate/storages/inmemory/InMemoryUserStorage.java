package ru.yandex.practicum.filmorate.storages.inmemory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Slf4j
@Component("inMemoryUser")
public class InMemoryUserStorage implements UserStorage {
    private long id = 0L;
    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        validate(user);

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("User {} with id {} is added.", user.getLogin(), user.getId());

        return user;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        validate(user);

        if (users.containsKey(user.getId())) {
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

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        if (id < 0) {
            throw new UserNotFoundException(String.format("Id %d is wrong, it must be more than zero.", id));
        }

        return users.get(id);
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    private long generateId() {
        return id += 1;
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Check your login, is can't contain white space.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("User's birthday is in future. Check it and try again.");
        }
    }
}
