package com.jaw.member.ui;

import java.time.format.DateTimeFormatter;

import com.jaw.member.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateResponseDTO {

    private Long id;
    private String name;
    private String nickname;
    private String birthDate;
    private String email;
    private String phoneNumber;

    @Builder
    public MemberUpdateResponseDTO(Long id, String name, String nickname, String birthDate, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public MemberUpdateResponseDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.birthDate = member.getBirthDate().format(DateTimeFormatter.ISO_DATE);
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
    }
}
