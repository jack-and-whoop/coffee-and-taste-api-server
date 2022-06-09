package com.jaw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginFailedException extends RuntimeException {

	private static final String MESSAGE = "로그인을 실패했습니다. (현재 이메일 : %s)";

	public LoginFailedException(String email) {
		super(String.format(MESSAGE, email));
	}
}
