package com.jaw.member.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class MemberRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("새로운 회원을 등록한다.")
	@Test
	void create() throws Exception {
		MemberRequestDTO request = MemberRequestDTO.builder()
			.name("홍길동")
			.nickname("hong")
			.birthDate(LocalDate.of(2000, 1, 1))
			.email("hong@gmail.com")
			.phoneNumber("010-1234-5678")
			.build();

		mvc.perform(post("/api/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("홍길동"))
			.andExpect(jsonPath("$.nickname").value("hong"))
			.andExpect(jsonPath("$.birthDate").value(LocalDate.of(2000, 1, 1).format(DateTimeFormatter.ISO_DATE)))
			.andExpect(jsonPath("$.email").value("hong@gmail.com"))
			.andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"));
	}
}
