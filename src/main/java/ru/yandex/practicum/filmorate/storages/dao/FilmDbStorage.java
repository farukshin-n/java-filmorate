package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.FilmServiceProcessingException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component("dbFilm")
@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

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

        List<Genre> newGenres = film.getGenre();
        for (Genre genre : newGenres) {
            String genreSqlQuery = "update film_genre set film_id = ?, genre_id = ? if conflict do nothing";
            jdbcTemplate.update(genreSqlQuery, film.getId(), genre.getGenreId());
        }

        return film;
    }


    @Override
    public void deleteFilm(Film film) {
        String sqlQuery = "delete from films where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film updateFilm(Film film) throws FilmNotFoundException {
        if (film == null) {
            throw new FilmNotFoundException("Film is null");
        }

        String sqlQueryFilms = "update films set " +
                "name = ?, description = ?, mpa_id = ?, release_date = ?, duration = ? " +
                "where film_id = ?";

        jdbcTemplate.update(sqlQueryFilms,
                film.getName(),
                film.getDescription(),
                film.getRatingMPA(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());

        List<Genre> newGenres = film.getGenre();
        for (Genre genre : newGenres) {
            String genreSqlQuery = "update film_genre set film_id = ?, genre_id = ?";
            jdbcTemplate.update(genreSqlQuery, film.getId(), genre.getGenreId());
        }

        return film;
    }

    @Override
    public Film getFilm(Long id) {
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_name, f.release_date, f.duration " +
                "from films f " +
                "join mpa m on m.mpa_id=f.mpa_id " +
                "where f.film_id = ?";

        Film resultFilm = jdbcTemplate.queryForObject(sqlQuery, this::makeFilm, id);
        resultFilm.setGenre(genreStorage.loadFilmGenre(id));

        return resultFilm;
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film resultFilm = new Film(rs.getLong("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getLong("mpa_id"),
                rs.getDate("release_date").toLocalDate(),
                rs.getLong("duration"));

        resultFilm.setGenre(genreStorage.loadFilmGenre(resultFilm.getId()));

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
