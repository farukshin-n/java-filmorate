package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ProcessingException;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.FriendshipStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.util.List;

@Slf4j
@Component("dbFriendship")
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate, @Qualifier("dbUser") UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public Friendship addFriendship(Long id, Long friendId) {
        if (userStorage.getUser(id) == null) {
            throw new SubstanceNotFoundException(String.format("User with id %d not found.", id));
        } else if (userStorage.getUser(friendId) == null) {
            throw new SubstanceNotFoundException(String.format("User with id %d not found.", friendId));
        }

        String sqlQuery = "insert into friends(friend_one, friend_two) " +
                "values(?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
        log.info("To user with id {} added friend with id {}", id, friendId);
        return new Friendship(id, friendId);
    }

    @Override
    public void deleteFriendship(Long id, Long friendId) {
        String sqlQuery = "delete from friends where friend_one = ? and friend_two = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
        log.info("From user with id {} delete friend with id {}", id, friendId);
    }

    @Override
    public List<User> getFriendsOfUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ProcessingException(String.format(
                    "Parameter %s is less than or equal zero or equal null.",
                    userId));
        }
        String sqlQuery = "select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from friends f " +
                "join users u on f.friend_two=u.user_id " +
                "where f.friend_one = ?";

        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
    }

    @Override
    public List<User> getMutualFriendList(Long userOneId, Long userTwoId) {
        String sqlQuery = "select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from users u " +
                "join friends one on u.user_id=one.friend_two " +
                "join friends two on u.user_id=two.friend_two " +
                "where one.friend_one = ? and two.friend_one = ?";

        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userOneId, userTwoId);
    }
}
