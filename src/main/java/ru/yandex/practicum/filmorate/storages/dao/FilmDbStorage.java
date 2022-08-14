package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("dbFilm")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films(film_id, name, description, " +
                "mpa_id, release_date, duration) " +
                "values(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getRatingMPA(),
                film.getReleaseDate(),
                film.getDuration());

        film.setGenre(loadFilmGenre(film.getId()));

        return film;
    }

    @Override
    public List<Genre> loadFilmGenre(Long filmId) {
        String sqlQuery = "select genre_id, genre_name from genre " +
                "where genre_id in " +
                "(select genre_id from film_genre where film_id = ?)";

        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("genre_id"),
                rs.getString("genre_name"));
    }

    @Override
    public void deleteFilm(Film film) {
        String sqlQuery = "delete from films where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "name = ?, description = ?, mpa_id = ?, release_date = ?, duration = ? " +
                "where film_id = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getRatingMPA(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());

        film.setGenre(loadFilmGenre(film.getId()));

        return film;
    }

    @Override
    public Film getFilm(Long id) {
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_name, f.release_date, f.duration " +
                "from films f " +
                "join mpa m on m.mpa_id=f.mpa_id " +
                "where f.film_id = ?";

        Film resultFilm = jdbcTemplate.queryForObject(sqlQuery, this::makeFilm, id);
        resultFilm.setGenre(loadFilmGenre(id));

        return resultFilm;
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film resultFilm = new Film(rs.getLong("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("mpa_name"),
                rs.getDate("release_date").toLocalDate(),
                rs.getLong("duration"));

        resultFilm.setGenre(loadFilmGenre(resultFilm.getId()));

        return resultFilm;
    }

    @Override
    public List<Film> getFilmList() {
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_name, f.release_date, f.duration " +
                "from films f " +
                "join genre g on f.genre_id=g.genre_id " +
                "join mpa m on m.mpa_id=f.mpa_id " +
                "order by f.film_id";

        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        if (count == null || count <= 0) {
            throw new FilmServiceProcessingException("Parameter %s mustn't be less or equal zero or null.");
        }

        // check this query
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_name, f.release_date, f.duration " +
                "from films f " +
                "join mpa m on m.mpa_id=f.mpa_id " +
                "left join likes l on f.film_id=l.film_id " +
                "group by f.film_id, m.mpa_name order by COUNT(l.user_id) desc limit(" + count + ")";

        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }
}
