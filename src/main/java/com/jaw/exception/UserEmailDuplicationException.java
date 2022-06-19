package com.jaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailDuplicationException extends RuntimeException {

    private static final String MESSAGE = "이미 등록된 이메일입니다 : %s";

    public UserEmailDuplicationException(String email) {
        super(String.format(MESSAGE, email));
    }
}
