package ru.yandex.practicum.filmorate.exceptions;

public class FriendAddingException extends RuntimeException {
    public FriendAddingException(String s, long id, long userAddingId) {
        super(s);
    }
}
