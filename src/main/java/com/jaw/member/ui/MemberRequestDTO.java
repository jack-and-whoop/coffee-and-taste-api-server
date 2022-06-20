package com.jaw.member.ui;

import java.time.LocalDate;

import com.jaw.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MemberRequestDTO {

	private String name;
	private String nickname;
	private LocalDate birthDate;
	private String email;
	private String phoneNumber;

	@Setter
	private String password;

	@Builder
	public MemberRequestDTO(String name, String nickname, LocalDate birthDate, String email, String password, String phoneNumber) {
		this.name = name;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public Member toEntity() {
		return Member.builder()
			.name(name)
			.nickname(nickname)
			.birthDate(birthDate)
			.email(email)
			.password(password)
			.phoneNumber(phoneNumber)
			.build();
	}
}
