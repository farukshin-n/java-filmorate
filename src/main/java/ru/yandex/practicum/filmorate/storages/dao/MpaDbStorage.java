package ru.yandex.practicum.filmorate.storages.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storages.interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component("dbMpa")
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getMpaList() {
        String sqlQuery = "select mpa_id, mpa_name from mpa";
        return jdbcTemplate.query(sqlQuery, this::makeMpa);
    }

    private Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getLong("mpa_id"),
                rs.getString("mpa_name"));
    }

    @Override
    public Mpa getMpa(Long mpaId) {
        String sqlQuery = "select mpa_id, mpa_name from mpa where mpa_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeMpa, mpaId);
    }
}
