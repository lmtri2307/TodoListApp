package com.example.todolist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {
    EMAIL_DUPLICATED("This email has been used", HttpStatus.UNPROCESSABLE_ENTITY),
    EMAIL_NOT_FOUND("Invalid Email", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_PASSWORD("Wrong password", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_TODO("To-do not found", HttpStatus.UNPROCESSABLE_ENTITY),
    ;

    private final String message;
    private final HttpStatus status;

}
