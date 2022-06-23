package com.jaw.member.ui;

import static com.jaw.Fixtures.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jaw.member.application.AuthenticationService;
import com.jaw.member.application.MemberService;

@WebMvcTest(MemberRestController.class)
class MemberRestControllerTest {

	private static final String BASE_URI = "/api/members";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private AuthenticationService authenticationService;

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	private MemberRequestDTO request;
	private MemberResponseDTO response;

	@BeforeEach
	void setup() {
		request = MemberRequestDTO.builder()
			.build();

		response = new MemberResponseDTO(member());

		given(memberService.create(any(MemberRequestDTO.class))).willReturn(response);
		given(memberService.findAll()).willReturn(List.of(response));
	}

	@DisplayName("새로운 회원을 등록한다.")
	@Test
	void create() throws Exception {
		mvc.perform(post(BASE_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value("홍길동"))
			.andExpect(jsonPath("$.nickname").value("hong"))
			.andExpect(jsonPath("$.birthDate").value(LocalDate.of(1992, 1, 1).format(DateTimeFormatter.ISO_DATE)))
			.andExpect(jsonPath("$.email").value("hong@example.com"))
			.andExpect(jsonPath("$.phoneNumber").value("010-1234-5678"));
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() throws Exception {
		mvc.perform(get(BASE_URI))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(List.of(response))));
	}
}
