package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@Component("dbGenre")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenreList() {
        String sqlQuery = "select genre_id, genre_name from genres";
        return jdbcTemplate.query(sqlQuery, this::makeGenre);
    }

    @Override
    public Genre getGenre(Long genreId) {
        String sqlQuery = "select genre_id, genre_name from genres where genre_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId);
    }

    @Override
    public Film updateFilmGenreList(Film film) {
        String sqlDeleteGenresQuery = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sqlDeleteGenresQuery, film.getId());

        String sqlUpdateGenresQuery = "insert into film_genre(film_id, genre_id) values(?, ?)";

        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlUpdateGenresQuery, film.getId(), genre.getId());
        }
        log.info("Updated genre list of film with id {}", film.getId());
        return film;
    }

    @Override
    public void setFilmGenreList(Film film) {
        String sqlQuery = "select g.genre_id, g.genre_name " +
                "from film_genre fg " +
                "join genres g on fg.genre_id=g.genre_id " +
                "where fg.film_id = ?";

        List<Genre> genreList = jdbcTemplate.query(sqlQuery, this::makeGenre, film.getId());

        film.setGenres(new LinkedHashSet<>(genreList));
        log.info("Set genre list to film with id {}", film.getId());
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"),
                rs.getString("genre_name"));
    }
}
