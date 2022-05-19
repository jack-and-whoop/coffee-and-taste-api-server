package com.jaw.member.ui;

import java.time.LocalDate;

import lombok.Builder;
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

	@Builder
	public MemberResponseDTO(Long id, String name, String nickname, LocalDate birthDate, String email, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
