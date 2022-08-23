package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {
    Friendship addFriendship(Long id, Long friendId);

    void deleteFriendship(Long id, Long friendId);

    List<User> getFriendsOfUser(Long userId);
    List<User> getMutualFriendList(Long userOneId, Long userTwoId);
}
