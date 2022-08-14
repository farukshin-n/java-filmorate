package ru.yandex.practicum.filmorate.storages.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("dbGenre")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenreList() {
        String sqlQuery = "select name from genre";
        return jdbcTemplate.query(sqlQuery, this::makeGenre);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"),
                rs.getString("genre_name"));
    }

    @Override
    public Genre getGenre(Long genreId) {
        String sqlQuery = "select genre_name from genre where genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId);
    }
}
