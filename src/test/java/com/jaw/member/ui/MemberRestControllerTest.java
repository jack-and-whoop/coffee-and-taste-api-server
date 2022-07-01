package com.jaw.member.ui;

import com.jaw.member.application.AuthenticationService;
import com.jaw.member.application.MemberService;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.jaw.Fixtures.OBJECT_MAPPER;
import static com.jaw.Fixtures.member;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberRestController.class)
class MemberRestControllerTest {

	private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbfw";
	private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.1Zx-1BRb0VJflU1JBYaP_FqrL6S53uRBn5DhYablbf0";
	private static final String BASE_URI = "/api/members";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private AuthenticationService authenticationService;

	private MemberRequestDTO request;
	private MemberResponseDTO response;

	@BeforeEach
	void setup() {
		Member member = member();

		request = MemberRequestDTO.builder()
			.name(member.getName())
			.nickname(member.getNickname())
			.birthDate(member.getBirthDate())
			.email(member.getEmail())
			.phoneNumber(member.getPhoneNumber())
			.build();

		response = new MemberResponseDTO(member);

		given(authenticationService.roles(member.getId())).willReturn(List.of(new Role(1L, "USER")));
		given(authenticationService.parseToken(VALID_TOKEN)).willReturn(member.getId());

		given(memberService.create(any(MemberRequestDTO.class))).willReturn(response);
		given(memberService.findAll()).willReturn(List.of(response));
		given(memberService.findById(any(Long.class))).willReturn(response);
	}

	@DisplayName("새로운 회원을 등록한다.")
	@Test
	void create() throws Exception {
		mvc.perform(post(BASE_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(request)))
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
			.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(List.of(response))));
	}

	@DisplayName("특정 회원을 조회한다.")
	@Test
	void findById() throws Exception {
		mvc.perform(get(BASE_URI + "/me")
				.header("Authorization", "Bearer " + VALID_TOKEN))
			.andExpect(status().isOk());
	}

	@DisplayName("인증 정보가 유효하지 않다면, 특정 회원을 조회할 수 없다.")
	@Test
	void findByIdFailed() throws Exception {
		mvc.perform(get(BASE_URI + "/me")
				.header("Authorization", "Bearer " + INVALID_TOKEN))
			.andExpect(status().isForbidden());
	}
}
