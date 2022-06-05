package com.jaw.member.ui;

import java.time.LocalDate;

import com.jaw.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponseDTO {

	private final Long id;
	private final String name;
	private final String nickname;
	private final LocalDate birthDate;
	private final String email;
	private final String phoneNumber;

	public MemberResponseDTO(Member member) {
		this.id = member.getId();
		this.name = member.getName();
		this.nickname = member.getNickname();
		this.birthDate = member.getBirthDate();
		this.email = member.getEmail();
		this.phoneNumber = member.getPhoneNumber();
	}
}
