package com.jaw;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jaw.member.domain.Member;

public class Fixtures {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	private Fixtures() {
	}

	public static Member member() {
		return Member.builder()
			.id(1L)
			.name("홍길동")
			.nickname("hong")
			.email("hong@example.com")
			.password(PASSWORD_ENCODER.encode("1234"))
			.birthDate(LocalDate.of(1992, 1, 1))
			.phoneNumber("010-1234-5678")
			.build();
	}

	public static Member other() {
		return Member.builder()
			.id(2L)
			.name("김길동")
			.nickname("kim")
			.email("kim@example.com")
			.password(PASSWORD_ENCODER.encode("5678"))
			.birthDate(LocalDate.of(1994, 1, 1))
			.phoneNumber("010-2222-3333")
			.build();
	}
}
