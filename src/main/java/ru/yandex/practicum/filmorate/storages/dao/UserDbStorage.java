package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component("dbUser")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users(user_id, email, login, name, birthday) " +
                "values(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery = "delete from users where user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        String sqlQuery = "update users set " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {
        String sqlQuery = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeUser, id);
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate());
    }

    @Override
    public List<User> getUserList() {
        String sqlQuery = "select user_id, email, login, name, birthday from users";
        return jdbcTemplate.query(sqlQuery, this::makeUser);
    }
}
