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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component("dbGenre")
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenreList() {
        String sqlQuery = "select distinct genre_id, genre_name from genre";
        List<Genre> resultGenres = jdbcTemplate.query(sqlQuery, this::makeGenre);

        if (resultGenres.size() == 0) {
            log.info("Genre list is empty.");
        }

        return resultGenres;
    }

    @Override
    public Genre getGenre(Long genreId) {
        String sqlQuery = "select distinct genre_id, genre_name from genre where genre_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::makeGenre, genreId);
    }

    public Film updateFilmGenreList(Film film) {
        String sqlDeleteGenresQuery = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sqlDeleteGenresQuery, film.getId());

        String sqlUpdateGenresQuery = "insert into film_genre(film_id, genre_id) values(?, ?)";

        for (Genre genre : film.getGenre()) {
            jdbcTemplate.update(sqlUpdateGenresQuery, film.getId(), genre.getGenreId());
        }
        return film;
    }

    public void setFilmGenreList(Film film) {
        String sqlQuery = "select g.genre_id, g.genre_name " +
                "from film_genre fg " +
                "join genre g on fg.genre_id=g.genre_id " +
                "where fg.film_id = ?";

        List<Genre> genreList = jdbcTemplate.query(sqlQuery, this::makeGenre, film.getId());

        film.setGenre(new HashSet<>(genreList));
    }


    public Set<Genre> loadFilmGenreList(Long filmId) {
        String sqlQuery = "select distinct genre_id, genre_name from genre " +
                "where genre_id in " +
                "(select genre_id from film_genre where film_id = ?)";

        List<Genre> genreList = jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);

        return new HashSet<>(genreList);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"),
                rs.getString("genre_name"));
    }
}
