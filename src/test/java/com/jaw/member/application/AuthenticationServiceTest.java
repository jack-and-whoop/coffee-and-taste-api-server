package com.jaw.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jaw.auth.JwtUtil;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;

class AuthenticationServiceTest {

	private static final String SECRET_KEY = "this-is-coffee-and-taste-api-server";
	private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbfw";
	private static final String VALID_EMAIL = "aaa@gmail.com";
	private static final String VALID_PASSWORD = "1234";
	private static final String INVALID_EMAIL = "bbb@gmail.com";
	private static final String INVALID_PASSWORD = "1111";

	private final MemberRepository memberRepository = mock(MemberRepository.class);
	private AuthenticationService authenticationService;

	@BeforeEach
	void setup() {
		JwtUtil jwtUtil = new JwtUtil(SECRET_KEY);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		authenticationService = new AuthenticationService(memberRepository, jwtUtil, passwordEncoder);

		Member member = Member.builder()
			.id(1L)
			.password(passwordEncoder.encode(VALID_PASSWORD))
			.build();

		given(memberRepository.findByEmail(VALID_EMAIL)).willReturn(Optional.of(member));
	}

	@DisplayName("이메일과 비밀번호가 유효하다면 로그인이 성공한다.")
	@Test
	void loginWithValidEmailAndPassword() {
		String accessToken = authenticationService.login(VALID_EMAIL, VALID_PASSWORD);
		assertThat(accessToken).isEqualTo(VALID_TOKEN);
		verify(memberRepository).findByEmail(VALID_EMAIL);
	}

	@DisplayName("이메일이 유효하지 않을 경우, 예외가 발생한다.")
	@Test
	void loginWithInvalidEmail() {
		assertThatThrownBy(() -> authenticationService.login(INVALID_EMAIL, VALID_PASSWORD))
			.isInstanceOf(IllegalArgumentException.class);
		verify(memberRepository).findByEmail(INVALID_EMAIL);
	}

	@DisplayName("비밀번호가 유효하지 않을 경우, 예외가 발생한다.")
	@Test
	void loginWithInvalidPassword() {
		assertThatThrownBy(() -> authenticationService.login(VALID_EMAIL, INVALID_PASSWORD))
			.isInstanceOf(IllegalArgumentException.class);
		verify(memberRepository).findByEmail(VALID_EMAIL);
	}
}
