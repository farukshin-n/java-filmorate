package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.FriendshipStorage;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@Service("friendService")
public class FriendshipService {
    private final FriendshipStorage friendshipStorage;

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
