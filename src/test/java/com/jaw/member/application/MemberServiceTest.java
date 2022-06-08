package com.jaw.member.application;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;

class MemberServiceTest {

	private InMemoryMemberRepository memberRepository;
	private MemberService memberService;

	@BeforeEach
	void setup() {
		memberRepository = new InMemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@AfterEach
	void teardown() {
		memberRepository.clear();
	}

	@DisplayName("회원을 등록한다.")
	@Test
	void create() {
		MemberResponseDTO member = memberService.create(member( "memberA", "aaa@gmail.com"));
		assertThat(member.getId()).isEqualTo(1L);
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() {
		memberRepository.save(member("memberA", "aaa@gmail.com").toEntity());
		memberRepository.save(member("memberB", "bbb@gmail.com").toEntity());
		List<MemberResponseDTO> members = memberService.findAll();
		assertThat(members).hasSize(2);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		memberRepository.save(member("memberA", "aaa@gmail.com").toEntity());
		memberRepository.save(member("memberB", "bbb@gmail.com").toEntity());
		MemberResponseDTO member = memberService.findByEmail("aaa@gmail.com");
		assertThat(member.getName()).isEqualTo("memberA");
		assertThat(member.getEmail()).isEqualTo("aaa@gmail.com");
	}

	private MemberRequestDTO member(String name, String email) {
		return MemberRequestDTO.builder()
			.name(name)
			.email(email)
			.birthDate(LocalDate.now())
			.build();
	}
}
