package com.jaw.auth;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.exception.LoginFailedException;
import com.jaw.member.application.AuthenticationService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

	private static final String LOGIN_URI = "/api/auth/login";
	private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbfw";
	private static final String VALID_EMAIL = "aaa@gmail.com";
	private static final String VALID_PASSWORD = "1234";
	private static final String INVALID_EMAIL = "bbb@gmail.com";
	private static final String INVALID_PASSWORD = "1111";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AuthenticationService authenticationService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		given(authenticationService.login(VALID_EMAIL, VALID_PASSWORD))
			.willReturn(VALID_TOKEN);

		given(authenticationService.login(VALID_EMAIL, INVALID_PASSWORD))
			.willThrow(new LoginFailedException(VALID_EMAIL));

		given(authenticationService.login(INVALID_EMAIL, VALID_PASSWORD))
			.willThrow(new LoginFailedException(INVALID_EMAIL));
	}

	@DisplayName("유효한 이메일과 비밀번호로 로그인 시, 토큰이 반환된다.")
	@Test
	void loginWithValidEmailAndPassword() throws Exception {
		String requestBody = objectMapper.writeValueAsString(new LoginRequestDTO(VALID_EMAIL, VALID_PASSWORD));
		String responseBody = objectMapper.writeValueAsString(new LoginResponseDTO(VALID_TOKEN));

		mvc.perform(post(LOGIN_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(content().string(responseBody));
	}

	@DisplayName("이메일 또는 비밀번호가 유효하지 않을 경우, 로그인을 실패한다.")
	@MethodSource("invalidLoginRequestArguments")
	@ParameterizedTest
	void loginWithInvalidEmailOrPassword(String email, String password) throws Exception {
		String requestBody = objectMapper.writeValueAsString(new LoginRequestDTO(email, password));

		mvc.perform(post(LOGIN_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest());
	}

	private static Stream<Arguments> invalidLoginRequestArguments() {
		return Stream.of(
			Arguments.of(VALID_EMAIL, INVALID_PASSWORD),
			Arguments.of(INVALID_EMAIL, VALID_PASSWORD)
		);
	}
}
