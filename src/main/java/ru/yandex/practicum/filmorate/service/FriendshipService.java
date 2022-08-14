package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.FriendshipStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Service
public class FriendshipService {
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public FriendshipService(@Qualifier("dbFriendship") FriendshipStorage friendshipStorage) {
        this.friendshipStorage = friendshipStorage;
    }

    public void addFriendship(@Valid @Positive Long id,
                              @Valid @Positive Long friendId) {
        friendshipStorage.addFriendship(id, friendId);
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
