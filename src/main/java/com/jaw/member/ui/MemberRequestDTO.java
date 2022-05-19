package com.jaw.member.ui;

import java.time.LocalDate;

import com.jaw.member.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDTO {

	private String name;
	private String nickname;
	private LocalDate birthDate;
	private String email;
	private String phoneNumber;

	public Member toEntity() {
		return Member.builder()
			.name(name)
			.nickname(nickname)
			.birthDate(birthDate)
			.email(email)
			.phoneNumber(phoneNumber)
			.build();
	}
}
