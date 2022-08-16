package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.SubstanceNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storages.interfaces.MpaStorage;

import java.util.List;

@Data
@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(@Qualifier("dbMpa") MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getMpaList() {
        return mpaStorage.getMpaList();
    }

    public Mpa getMpa(Long mpaId) throws SubstanceNotFoundException {
        if (mpaId < 0) {
            throw new SubstanceNotFoundException(String.format("Mpa with id %d isn't exist.", mpaId));
        }
        Mpa resultMpa = mpaStorage.getMpa(mpaId);

        if (resultMpa == null) {
            throw new SubstanceNotFoundException(String.format("Mpa not found in database.", mpaId));
        }

        return resultMpa;
    }
}
