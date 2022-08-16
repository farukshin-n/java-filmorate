package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ProcessingException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.FriendshipStorage;

import java.util.List;

@Slf4j
@Component("dbFriendship")
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Friendship addFriendship(Long id, Long friendId) {
        String sqlQuery = "insert into friends(friend_one, friend_two) " +
                "values(?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
        return new Friendship(id, friendId);
    }

    @Override
    public void deleteFriendship(Long id, Long friendId) {
        String sqlQuery = "delete from friends where friend_one = ? and friend_two = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void acceptFriendship(Long id, Long friendId) {
        String sqlQuery = "insert into friends(friend_one, friend_two) " +
                "values(?, ?)";
        jdbcTemplate.update(sqlQuery, friendId, id);
    }

    @Override
    public List<User> getFriendsOfUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ProcessingException(String.format(
                    "Parameter %s is less than or equal zero or equal null.",
                    userId));
        }
        String sqlQuery = "select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from friends f join users u on f.friend_one=u.user_id where f.friend_two=" + userId +
                " union select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from friends f join users u on f.friend_two=u.user_id where f.friend_one=" + userId;

        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
    }

    @Override
    public List<User> getMutualFriendList(Long userOneId, Long userTwoId) {
        List<User> firstFriendList = getFriendsOfUser(userOneId);
        firstFriendList.retainAll(getFriendsOfUser(userTwoId));
        return firstFriendList;
    }
}
