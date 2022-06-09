package com.jaw.member.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;

	@Column(nullable = false)
	private String name;

	private String nickname;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phoneNumber;

	@Builder
	public Member(Long id, String name, String nickname, LocalDate birthDate, String email, String password, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(password, this.password);
	}
}
