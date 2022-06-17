package com.jaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 사용자입니다. (현재 이메일 : %s)";

    public MemberNotFoundException(String email) {
        super(String.format(MESSAGE, email));
    }
}
