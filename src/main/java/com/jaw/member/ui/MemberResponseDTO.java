package com.jaw.member.ui;

import java.time.LocalDate;

import com.jaw.member.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {

	private Long id;
	private String name;
	private String nickname;
	private LocalDate birthDate;
	private String email;
	private String phoneNumber;

	public MemberResponseDTO(Member member) {
		this.id = member.getId();
		this.name = member.getName();
		this.nickname = member.getNickname();
		this.birthDate = member.getBirthDate();
		this.email = member.getEmail();
		this.phoneNumber = member.getPhoneNumber();
	}
}
