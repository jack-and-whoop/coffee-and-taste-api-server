package com.jaw.auth;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jaw.exception.InvalidTokenException;

class JwtUtilTest {

	@DisplayName("사용자 id가 유효하면, 토큰을 생성 후 반환한다.")
	@ValueSource(longs = {1, Long.MAX_VALUE})
	@ParameterizedTest
	void encodeWithValidToken(Long userId) {
		String token = JWT_UTIL.encode(userId);
		assertThat(token).containsPattern("(^[\\w-]*\\.[\\w-]*\\.[\\w-]*$)");
	}

	@DisplayName("사용자 id가 유효하지 않으면, 예외가 발생한다.")
	@ValueSource(longs = {0, Long.MIN_VALUE})
	@NullSource
	@ParameterizedTest
	void encodeWithInvalidToken(Long userId) {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> JWT_UTIL.encode(userId));
	}

	@DisplayName("토큰이 유효한 경우, 사용자 정보를 반환한다.")
	@MethodSource("decodeValidArguments")
	@ParameterizedTest
	void decodeWithValidToken(Long id, String token) {
		Long decodedUserId = JWT_UTIL.decode(token);
		assertThat(decodedUserId).isEqualTo(id);
	}

	private static Stream<Arguments> decodeValidArguments() {
		return Stream.of(
			Arguments.of(1L, JWT_UTIL.encode(1L)),
			Arguments.of(Long.MAX_VALUE, JWT_UTIL.encode(Long.MAX_VALUE))
		);
	}

	@DisplayName("토큰이 유효하지 않은 경우, 예외가 발생한다.")
	@ValueSource(strings = {
		"eyJhbGciOiJIUzI1NiJ9",
		"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjkyMjMzNzIwMzY4NTQ3NzU4MDd9"
	})
	@NullAndEmptySource
	@ParameterizedTest
	void decodeWithInvalidToken(String token) {
		assertThatThrownBy(() -> JWT_UTIL.decode(token))
			.isInstanceOf(InvalidTokenException.class);
	}
}
