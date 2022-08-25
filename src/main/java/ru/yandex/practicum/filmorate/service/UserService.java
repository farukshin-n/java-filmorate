package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@Service("userService")
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserService(@Qualifier("dbUser") UserStorage userStorage, FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User getUser(Long id) throws SubstanceNotFoundException {
        if (id < 0) {
            throw new SubstanceNotFoundException(String.format("Id %d is less than zero", id));
        }

        final User user = userStorage.getUser(id);

        if (user == null) {
            throw new SubstanceNotFoundException(String.format("User not with id %d found.", id));
        }

        return user;
    }

    public User createUser(User user) {
        validate(user);

        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validate(user);
        final User resultUser = userStorage.getUser(user.getId());

        if (resultUser == null) {
            throw new SubstanceNotFoundException(String.format("User %d not found in database.", user.getId()));
        }

        return userStorage.updateUser(user);
    }

    public List<User> getUserList() {
        return userStorage.getUserList();
    }

    private User validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Check your login, is can't contain white space.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("User's birthday is in future. Check it and try again.");
        }
        return user;
    }

    public Friendship addFriendship(@Valid @Positive Long id,
                                    @Valid @Positive Long friendId) {
        return friendshipStorage.addFriendship(id, friendId);
    }

    public void deleteFriendship(@Valid @Positive Long id,
                                 @Valid @Positive Long friendId) {
        friendshipStorage.deleteFriendship(id, friendId);
    }

    public List<User> getFriendsList(@Valid @Positive Long userId) {
        return friendshipStorage.getFriendsOfUser(userId);
    }

    public List<User> getMutualFriendList(@Valid @Positive Long userOneId,
                                          @Valid @Positive Long userTwoId) {
        return friendshipStorage.getMutualFriendList(userOneId, userTwoId);
    }
}
