package com.jaw.member.domain;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("회원의 비밀번호를 암호화한다.")
    @Test
    void create() {
        Member member = member();
        assertThat(member.getPassword()).isNotEqualTo("1234");
    }

    @DisplayName("회원의 비밀번호가 일치하는지 확인한다.")
    @Test
    void authenticate() {
        Member member = member();
        assertAll(
            () -> assertThat(member.authenticate("1234", PASSWORD_ENCODER)).isTrue(),
            () -> assertThat(member.authenticate("5678", PASSWORD_ENCODER)).isFalse()
        );
    }
}
