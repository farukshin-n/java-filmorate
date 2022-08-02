package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendProcessingException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage storage;

    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(@Valid Long userAddingId, @Valid Long userAddedId) throws UserNotFoundException {
        helpAddingFriend(userAddingId, userAddedId);
        helpAddingFriend(userAddedId, userAddingId);
    }

    private void helpAddingFriend(Long firstUserId, Long secondUserId) throws UserNotFoundException {
        InMemoryUserStorage userStorage = (InMemoryUserStorage) storage;

        User user1 = userStorage.getUser(firstUserId);
        User user2 = userStorage.getUser(secondUserId);

        if (!user1.getFriends().add(user2)) {
            throw new FriendProcessingException(String.format("User %d not added to %d friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public void deleteFriend(@Valid Long userAddingId, @Valid Long userAddedId) throws UserNotFoundException {
        helpDeletingFriend(userAddingId, userAddedId);
        helpDeletingFriend(userAddedId, userAddingId);
    }

    private void helpDeletingFriend(Long firstUserId, Long secondUserId) throws UserNotFoundException {
        InMemoryUserStorage userStorage = (InMemoryUserStorage) storage;

        User user1 = userStorage.getUser(firstUserId);
        User user2 = userStorage.getUser(secondUserId);

        if (!user1.getFriends().remove(user2)) {
            throw new FriendProcessingException(String.format("User %d not deleted from user %d friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public ArrayList<User> getMutualFriendsList(@Valid Long userAddingId, @Valid Long userAddedId) {
        InMemoryUserStorage userStorage = (InMemoryUserStorage) storage;

        User userAdding = userStorage.getUser(userAddingId);
        User userAdded = userStorage.getUser(userAddedId);

        Set<User> intersection = new HashSet<>(userAdding.getFriends());
        if (!intersection.retainAll(userAdded.getFriends())) {
            throw new FriendProcessingException(String.format(
                    "Error during compiling mutual friends' list between %d and %d",
                    userAdded.getId(), userAdding.getId()));
        }
        return new ArrayList<>(intersection);
    }

    public ArrayList<User> getFriendList(Long userId) {
        return new ArrayList<>(((InMemoryUserStorage) storage).getUser(userId).getFriends());
    }
}
