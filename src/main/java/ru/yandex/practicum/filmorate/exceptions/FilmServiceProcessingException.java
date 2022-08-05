package ru.yandex.practicum.filmorate.exceptions;

public class FilmServiceProcessingException extends RuntimeException{
    public FilmServiceProcessingException(String s) {
        super(s);
    }
}
