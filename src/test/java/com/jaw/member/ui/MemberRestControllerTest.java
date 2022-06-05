package com.jaw.member.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaw.AbstractControllerTest;
import com.jaw.member.application.MemberService;

class MemberRestControllerTest extends AbstractControllerTest {

	private static final String BASE_URI = "/api/members";

	@Autowired
	private MemberService memberService;

	@DisplayName("새로운 회원을 등록한다.")
	@Test
	void create() throws Exception {
		MemberRequestDTO request = member("홍길동", "hong", "hong@gmail.com", "010-1234-5678");

		mvc.perform(post(BASE_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("홍길동"))
			.andExpect(jsonPath("$.nickname").value("hong"))
			.andExpect(jsonPath("$.birthDate").value(LocalDate.of(2000, 1, 1).format(DateTimeFormatter.ISO_DATE)))
			.andExpect(jsonPath("$.email").value("hong@gmail.com"))
			.andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"));
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() throws Exception {
		MemberResponseDTO kim = memberService.create(member("김철수", "kim", "kim@gmail.com", "010-1234-5678"));
		MemberResponseDTO park = memberService.create(member("박영희", "park", "park@gmail.com", "010-9012-3456"));

		mvc.perform(get(BASE_URI))
			.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(List.of(kim, park))));
	}

	private MemberRequestDTO member(String name, String nickname, String email, String phoneNumber) {
		return MemberRequestDTO.builder()
			.name(name)
			.nickname(nickname)
			.birthDate(LocalDate.of(2000, 1, 1))
			.email(email)
			.phoneNumber(phoneNumber)
			.build();
	}
}
