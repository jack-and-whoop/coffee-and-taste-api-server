package com.jaw.member.application;

import com.jaw.exception.MemberNotFoundException;
import com.jaw.exception.UserEmailDuplicationException;
import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

	private InMemoryMemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;
	private MemberService memberService;

	@BeforeEach
	void setup() {
		memberRepository = new InMemoryMemberRepository();
		passwordEncoder = new BCryptPasswordEncoder();
		memberService = new MemberService(memberRepository, passwordEncoder);
	}

	@AfterEach
	void teardown() {
		memberRepository.clear();
	}

	@DisplayName("회원을 등록한다.")
	@Test
	void create() {
		MemberResponseDTO member = memberService.create(member( "memberA", "aaa@gmail.com", "1234"));
		assertThat(member.getId()).isEqualTo(1L);
	}

	@DisplayName("회원 등록 시, 이미 등록된 이메일이라면 등록할 수 없다.")
	@Test
	void createWithDuplicateEmail() {
		memberService.create(member("memberA", "aaa@gmail.com", "1234"));
		MemberRequestDTO createMemberRequest = member("memberB", "aaa@gmail.com", "5678");

		assertThatThrownBy(() -> memberService.create(createMemberRequest))
			.isInstanceOf(UserEmailDuplicationException.class);
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() {
		memberRepository.save(member("memberA", "aaa@gmail.com", "1234").toEntity());
		memberRepository.save(member("memberB", "bbb@gmail.com", "5678").toEntity());
		List<MemberResponseDTO> members = memberService.findAll();
		assertThat(members).hasSize(2);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		memberRepository.save(member("memberA", "aaa@gmail.com", "1234").toEntity());
		memberRepository.save(member("memberB", "bbb@gmail.com", "5678").toEntity());
		MemberResponseDTO member = memberService.findByEmail("aaa@gmail.com");
		assertThat(member.getName()).isEqualTo("memberA");
		assertThat(member.getEmail()).isEqualTo("aaa@gmail.com");
	}

	@DisplayName("존재하지 않는 이메일로 회원을 조회할 경우, MemberNotFoundException 예외가 발생한다.")
	@Test
	void findByNonExistentEmail() {
		memberRepository.save(member("memberA", "aaa@gmail.com", "1234").toEntity());

		assertThatThrownBy(() -> memberService.findByEmail("bbb@gmail.com"))
			.isInstanceOf(MemberNotFoundException.class);
	}

	private MemberRequestDTO member(String name, String email, String password) {
		return MemberRequestDTO.builder()
			.name(name)
			.email(email)
			.password(passwordEncoder.encode(password))
			.birthDate(LocalDate.now())
			.build();
	}
}
