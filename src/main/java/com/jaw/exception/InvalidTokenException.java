package com.jaw.exception;

public class InvalidTokenException extends RuntimeException {

	private static final String MESSAGE = "유효하지 않은 토큰입니다 : %s";

	public InvalidTokenException(String token) {
		super(String.format(MESSAGE, token));
	}
}
