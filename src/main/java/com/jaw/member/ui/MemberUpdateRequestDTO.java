package com.jaw.member.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequestDTO {

    private String name;
    private String nickname;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phoneNumber;

    @Builder
    public MemberUpdateRequestDTO(String name, String nickname, LocalDate birthDate, String email, String password, String phoneNumber) {
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
