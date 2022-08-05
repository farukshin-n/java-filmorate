package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FriendProcessingException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Service
public class UserService {
    private final UserStorage storage;

    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public void addFriend(@Valid @Positive Long userAddingId,
                          @Valid @Positive Long userAddedId) throws UserNotFoundException {
        helpAddingFriend(userAddingId, userAddedId);
        helpAddingFriend(userAddedId, userAddingId);
    }

    public User getUser(Long id) {
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

    private void helpAddingFriend(Long firstUserId, Long secondUserId) throws UserNotFoundException {
        User user1 = storage.getUser(firstUserId);
        User user2 = storage.getUser(secondUserId);

        if (!user1.getFriends().add(user2)) {
            throw new FriendProcessingException(String.format("User %d not added to %d friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public void deleteFriend(@Valid @Positive Long userAddingId,
                             @Valid @Positive Long userAddedId) throws UserNotFoundException {
        helpDeletingFriend(userAddingId, userAddedId);
        helpDeletingFriend(userAddedId, userAddingId);
    }

    private void helpDeletingFriend(Long firstUserId, Long secondUserId) throws UserNotFoundException {
        User user1 = storage.getUser(firstUserId);
        User user2 = storage.getUser(secondUserId);

        if (!user1.getFriends().remove(user2)) {
            throw new FriendProcessingException(String.format("User %d not deleted from user %d friends' list.",
                    user2.getId(), user1.getId()));
        }
        storage.updateUser(user1);
    }

    public ArrayList<User> getMutualFriendsList(@Valid @Positive Long userAddingId,
                                                @Valid @Positive Long userAddedId) throws UserNotFoundException {
        User userAdding = storage.getUser(userAddingId);
        User userAdded = storage.getUser(userAddedId);

        Set<User> intersection = new HashSet<>(userAdding.getFriends());
        intersection.retainAll(userAdded.getFriends());
        return new ArrayList<>(intersection);
    }

    public ArrayList<User> getFriendList(Long userId) throws UserNotFoundException {
        return new ArrayList<>(storage.getUser(userId).getFriends());
    }
}
