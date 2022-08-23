package ru.yandex.practicum.filmorate.storages.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storages.interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storages.interfaces.MpaStorage;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Component("dbFilm")
@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films(name, release_date, description, duration, mpa_id) " +
                "values(?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(2, Types.DATE);
            } else {
                stmt.setDate(2, Date.valueOf(releaseDate));
            }
            stmt.setString(3, film.getDescription());
            stmt.setLong(4, film.getDuration());
            if (film.getMpa() == null) {
                stmt.setNull(5, Types.LONGNVARCHAR);
            } else {
                stmt.setLong(5, film.getMpa().getMpaId());
            }
            return stmt;
        }, keyHolder);

        film.setId(keyHolder.getKey().longValue());

        log.info("Film {} with id {} is added to database.", film.getName(), film.getId());

        return film;
    }


    @Override
    public void deleteFilm(Film film) {
        String sqlQuery = "delete from films where film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());

        log.info("Film {} with id {} deleted from database.", film.getName(), film.getId());
    }

    @Override
    public Film updateFilm(Film film) {
        if (film == null) {
            throw new SubstanceNotFoundException("Film is not exist.");
        }

        String sqlQueryFilms = "update films set " +
                "name = ?, description = ?, mpa_id = ?, release_date = ?, duration = ? " +
                "where film_id = ?";

        jdbcTemplate.update(sqlQueryFilms,
                film.getName(),
                film.getDescription(),
                film.getMpa().getMpaId(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());

        log.info("Film {} with id {} is updated database.", film.getName(), film.getId());

        return film;
    }

    @Override
    public Film getFilm(Long id) throws SubstanceNotFoundException {
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_id, f.release_date, f.duration, " +
                "g.genre_id, g.genre_name " +
                "from film_genre fg " +
                "join genre g on fg.genre_id=g.genre_id " +
                "join films f on f.film_id=fg.film_id " +
                "join mpa m on f.mpa_id=m.mpa_id " +
                "where fg.film_id = ?";

        // reviewer: genre тоже желательно джоинить одним запросом

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sqlQuery, id);

        Set<Film> resultFilmSet = makeFilm(sqlRowSet);
        if (resultFilmSet.size() == 0) {
            throw new SubstanceNotFoundException(String.format("Film with id %d is not exist in database", id));
        }

        return new ArrayList<>(resultFilmSet).get(0);
    }

    private Set<Film> makeFilm(SqlRowSet srs) {
        Film resultFilm = null;
        Set<Film> resultFilmSet = new HashSet<>();
        long count = -1L;

        while(srs.next()) {
            Genre newGenre = new Genre(
                    srs.getLong("genre_id"),
                    srs.getString("genre_name")
            );
            if (srs.getLong("film_id") != count) {
                Set<Genre> genreSet = new HashSet<>();
                resultFilm = new Film(
                        srs.getString("name"),
                        Objects.requireNonNull(srs.getDate("release_date")).toLocalDate(),
                        srs.getString("description"),
                        srs.getLong("duration")
                );
                Long filmId = srs.getLong("film_id");
                resultFilm.setId(filmId);
                resultFilm.setMpa(mpaStorage.getMpaById(srs.getLong("mpa_id")));
                genreSet.add(newGenre);
                resultFilm.setGenre(genreSet);
                count = filmId;
            } else {
                Set<Genre> updatedGenreSet = resultFilm.getGenre();
                updatedGenreSet.add(newGenre);
                resultFilm.setGenre(updatedGenreSet);
            }
            resultFilmSet.add(resultFilm);
        }
        return resultFilmSet;
    }

    @Override
    public List<Film> getFilmList() throws SubstanceNotFoundException {
        String sqlQuery = "select f.film_id, f.name, f.description, m.mpa_name, f.release_date, f.duration, " +
                "g.genre_id, g.genre_name " +
                "from film_genre fg " +
                "join genre g on fg.genre_id=g.genre_id " +
                "join films f on f.film_id=fg.film_id " +
                "join mpa m on f.mpa_id=m.mpa_id " +
                "group by fg.genre_id";

        return new ArrayList<>(helpGetFilmList(sqlQuery));
    }

    // check later
    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        String sqlQuery = "select f.film_d, f.name, f.description, m.mpa_name, f.release_date, f.duration, " +
                "g.genre_id, g.genre_name " +
                "from film_genre fg " +
                "join genre g on fg.genre_id=g.genre_id " +
                "join films f on f.film_id=fg.film_id " +
                "join mpa m on f.mpa_id=m.mpa_id " +
                "left outer join likes l on f.film_id=l.film_id" +
                "group by f.film_id, g.genre_id, m.mpa_name " +
                "order by count(l.user_id) desc " +
                "limit " + count;

        return new ArrayList<>(helpGetFilmList(sqlQuery));
    }

    private Set<Film> helpGetFilmList(String sqlQuery) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery);

        Set<Film> resultFilmSet = makeFilm(rowSet);

        return resultFilmSet;
    }


}