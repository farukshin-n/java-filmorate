package ru.yandex.practicum.filmorate.storages.interfaces;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendshipStorage {
    public Friendship addFriendship(Long id, Long friendId);

    public void deleteFriendship(Long id, Long friendId);

    public void acceptFriendship(Long id, Long friendId);

    public List<User> getFriendsOfUser(Long userId);
    public List<User> getMutualFriendList(Long userOneId, Long userTwoId);
}
