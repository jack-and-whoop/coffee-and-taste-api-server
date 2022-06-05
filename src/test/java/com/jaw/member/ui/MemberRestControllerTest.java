package com.jaw.member.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaw.member.application.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
class MemberRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService memberService;

	@DisplayName("새로운 회원을 등록한다.")
	@Test
	void create() throws Exception {
		MemberRequestDTO request = member("홍길동", "hong", "hong@gmail.com", "010-1234-5678");

		mvc.perform(post("/api/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("홍길동"))
			.andExpect(jsonPath("$.nickname").value("hong"))
			.andExpect(jsonPath("$.birthDate").value(LocalDate.now().format(DateTimeFormatter.ISO_DATE)))
			.andExpect(jsonPath("$.email").value("hong@gmail.com"))
			.andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"));
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() throws Exception {
		MemberResponseDTO kim = memberService.create(member("김철수", "kim", "kim@gmail.com", "010-1234-5678"));
		MemberResponseDTO park = memberService.create(member("박영희", "park", "park@gmail.com", "010-9012-3456"));

		mvc.perform(get("/api/members"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(List.of(kim, park))));
	}

	private MemberRequestDTO member(String name, String nickname, String email, String phoneNumber) {
		return MemberRequestDTO.builder()
			.name(name)
			.nickname(nickname)
			.birthDate(LocalDate.now())
			.email(email)
			.phoneNumber(phoneNumber)
			.build();
	}
}
