package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(ValidationException e) {
        log.info("400 {}", e.getMessage(), e);
        return new ErrorResponse("Validation error", e.getMessage());
    }

    @ExceptionHandler({FilmNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundCases(IOException e) {
        log.info("404 {}", e.getMessage(), e);
        return new ErrorResponse("Object not found", e.getMessage());
    }

    @ExceptionHandler({FilmServiceProcessingException.class, FriendProcessingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalErrors(RuntimeException e) {
        log.info("500 {}", e.getMessage(), e);
        return new ErrorResponse("Internal processing error", e.getMessage());
    }
}

