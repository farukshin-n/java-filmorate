package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("dbGenre")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenreList() {
        String sqlQuery = "select distinct genre_name from genre";
        return jdbcTemplate.query(sqlQuery, this::makeGenre);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"),
                rs.getString("genre_name"));
    }

    @Override
    public Genre getGenre(Long genreId) {
        String sqlQuery = "select distinct genre_id, genre_name from genre where genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId);
    }

    @Override
    public List<Genre> loadFilmGenre(Long filmId) {
        String sqlQuery = "select distinct genre_id, genre_name from genre " +
                "where genre_id in " +
                "(select genre_id from film_genre where film_id = ?)";

        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }
}
