package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendProcessingException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
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

    public void addFriend(@Valid User userAdding, @Valid User userAdded) throws UserNotFoundException {
        helpAddingFriend(userAdding, userAdded);
        helpAddingFriend(userAdded, userAdding);
    }

    private void helpAddingFriend(User user1, User user2) throws UserNotFoundException {
        if (!user1.getFriends().add(user2.getId())) {
            throw new FriendProcessingException(String.format("User %l not added to %l friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public void deleteFriend(@Valid User userAdding, @Valid User userAdded) throws UserNotFoundException {
        helpDeletingFriend(userAdding, userAdded);
        helpDeletingFriend(userAdded, userAdding);
    }

    private void helpDeletingFriend(User user1, User user2) throws UserNotFoundException {
        if (!user1.getFriends().remove(user2.getId())) {
            throw new FriendProcessingException(String.format("User %l not deleted from user %l friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public ArrayList<Long> showMutualFriendsList(@Valid User userAdding, @Valid User userAdded) {
        Set<Long> intersection = new HashSet<Long>(userAdding.getFriends());
        if (!intersection.retainAll(userAdded.getFriends())) {
            throw new FriendProcessingException(String.format("Error during compiling mutual friends' list between %l and %l",
                    userAdded.getId(), userAdding.getId()));
        }
        return new ArrayList<Long>(intersection);
    }
}
