package com.jaw.member.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jaw.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberResponseDTO {

	private final Long id;
	private final String name;
	private final String nickname;
	private final String birthDate;
	private final String email;
	private final String phoneNumber;

	public MemberResponseDTO(Member member) {
		this.id = member.getId();
		this.name = member.getName();
		this.nickname = member.getNickname();
		this.birthDate = member.getBirthDate().format(DateTimeFormatter.ISO_DATE);
		this.email = member.getEmail();
		this.phoneNumber = member.getPhoneNumber();
	}
}
