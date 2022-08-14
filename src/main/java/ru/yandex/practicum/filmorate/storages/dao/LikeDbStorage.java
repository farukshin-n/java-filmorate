package ru.yandex.practicum.filmorate.storages.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.interfaces.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("dbLike")
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Like addLike(Long filmId, Long userId) {
        String sqlQuery = "insert into likes(film_id, user_id) values(?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        return new Like(filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sqlQuery = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }
}
