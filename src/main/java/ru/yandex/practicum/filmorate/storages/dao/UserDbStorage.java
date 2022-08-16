package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ProcessingException;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.UserStorage;


import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component("dbUser")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users(login, name, email, birthday) values(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());

        log.info("User {} with id {} is added.", user.getLogin(), user.getId());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery = "delete from users where user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User updateUser(User user) throws SubstanceNotFoundException {
        String sqlQuery = "update users set " +
                "login = ?, name = ?, email = ?, birthday = ? " +
                "where user_id = ?";

        try {
            jdbcTemplate.update(sqlQuery,
                    user.getLogin(),
                    user.getName(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getId());
        } catch(DataAccessException e) {
            throw new ProcessingException("Went smth wrong during getting user from database.");
        }

        log.info("User {} with email {} is updated.", user.getLogin(), user.getEmail());
        return getUser(user.getId());
    }

    @Override
    public User getUser(Long id) {
        int checkUser = jdbcTemplate.update(
                "update users set user_id=? where user_id=?", id, id);
        if (checkUser == 0) {
            return null;
        }

        String sqlQuery = "select user_id, login, name, email, birthday " +
                "from users where user_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, UserDbStorage::makeUser, id);
    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("user_id"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate());
    }

    @Override
    public List<User> getUserList() {
        String sqlQuery = "select user_id, login, name, email, birthday from users";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
    }
}
