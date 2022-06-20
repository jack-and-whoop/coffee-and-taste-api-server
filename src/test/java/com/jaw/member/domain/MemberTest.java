package com.jaw.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("회원의 비밀번호를 암호화한다.")
    @Test
    void create() {
        Member member = Member.builder()
            .password(passwordEncoder.encode("1234"))
            .build();

        assertThat(member.getPassword()).isNotEqualTo("1234");
    }

    @DisplayName("회원의 비밀번호가 일치하는지 확인한다.")
    @Test
    void authenticate() {
        Member member = Member.builder()
            .password(passwordEncoder.encode("1234"))
            .build();

        assertAll(
            () -> assertThat(member.authenticate("1234", passwordEncoder)).isTrue(),
            () -> assertThat(member.authenticate("5678", passwordEncoder)).isFalse()
        );
    }
}
