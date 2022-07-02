package com.jaw.member.application;

import static com.jaw.Fixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jaw.exception.MemberNotFoundException;
import com.jaw.exception.UserEmailDuplicationException;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.Role;
import com.jaw.member.domain.RoleRepository;
import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;
import com.jaw.member.ui.MemberUpdateRequestDTO;

class MemberServiceTest {

	private InMemoryMemberRepository memberRepository;
	private final RoleRepository roleRepository = mock(RoleRepository.class);
	private PasswordEncoder passwordEncoder;
	private MemberService memberService;

	@BeforeEach
	void setup() {
		memberRepository = new InMemoryMemberRepository();
		passwordEncoder = new BCryptPasswordEncoder();
		memberService = new MemberService(memberRepository, roleRepository, passwordEncoder);
	}

	@AfterEach
	void teardown() {
		memberRepository.clear();
	}

	@DisplayName("회원을 등록한다.")
	@Test
	void create() {
		MemberResponseDTO member = memberService.create(memberCreateRequest("memberA", "aaa@gmail.com", "1234"));
		assertThat(member.getId()).isEqualTo(1L);

		verify(roleRepository).save(any(Role.class));
	}

	@DisplayName("회원 등록 시, 이미 등록된 이메일이라면 등록할 수 없다.")
	@Test
	void createWithDuplicateEmail() {
		memberService.create(memberCreateRequest("memberA", "aaa@gmail.com", "1234"));
		MemberRequestDTO createMemberRequest = memberCreateRequest("memberB", "aaa@gmail.com", "5678");

		assertThatThrownBy(() -> memberService.create(createMemberRequest))
			.isInstanceOf(UserEmailDuplicationException.class);
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() {
		memberService.create(memberCreateRequest("memberA", "aaa@gmail.com", "1234"));
		memberService.create(memberCreateRequest("memberB", "bbb@gmail.com", "5678"));
		List<MemberResponseDTO> members = memberService.findAll();
		assertThat(members).hasSize(2);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		memberService.create(memberCreateRequest("memberA", "aaa@gmail.com", "1234"));
		memberService.create(memberCreateRequest("memberB", "bbb@gmail.com", "5678"));
		MemberResponseDTO member = memberService.findByEmail("aaa@gmail.com");
		assertThat(member.getName()).isEqualTo("memberA");
		assertThat(member.getEmail()).isEqualTo("aaa@gmail.com");
	}

	@DisplayName("존재하지 않는 이메일로 회원을 조회할 경우, MemberNotFoundException 예외가 발생한다.")
	@Test
	void findByNonExistentEmail() {
		memberService.create(memberCreateRequest("memberA", "aaa@gmail.com", "1234"));
		assertThatThrownBy(() -> memberService.findByEmail("bbb@gmail.com"))
			.isInstanceOf(MemberNotFoundException.class);
	}

	private MemberRequestDTO memberCreateRequest(String name, String email, String password) {
		return MemberRequestDTO.builder()
			.name(name)
			.email(email)
			.password(passwordEncoder.encode(password))
			.birthDate(LocalDate.now())
			.build();
	}

	@DisplayName("회원 정보를 수정한다.")
	@Test
	void update() {
		Member member = memberRepository.save(member());
		MemberUpdateRequestDTO updateRequest = MemberUpdateRequestDTO.builder()
			.name("김길동")
			.nickname("kim")
			.birthDate(LocalDate.of(1995, 1, 1))
			.email("kim@example.com")
			.phoneNumber("010-2222-3333")
			.build();

		memberService.update(member.getId(), updateRequest);

		Member result = memberRepository.findById(member.getId())
			.orElseThrow(IllegalArgumentException::new);

		assertAll(
			() -> assertThat(result.getId()).isEqualTo(member.getId()),
			() -> assertThat(result.getName()).isEqualTo("김길동"),
			() -> assertThat(result.getNickname()).isEqualTo("kim"),
			() -> assertThat(result.getBirthDate()).isEqualTo("1995-01-01"),
			() -> assertThat(result.getEmail()).isEqualTo("kim@example.com"),
			() -> assertThat(result.getPhoneNumber()).isEqualTo("010-2222-3333")
		);
	}
}
