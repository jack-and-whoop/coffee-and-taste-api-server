package com.jaw.member.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;

class MemberServiceTest {

	private MemberRepository memberRepository;
	private MemberService memberService;

	@BeforeEach
	void setup() {
		memberRepository = new InMemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}

	@DisplayName("회원을 등록한다.")
	@Test
	void create() {
		MemberResponseDTO member = memberService.create(member( "memberA"));
		assertThat(member.getId()).isEqualTo(1L);
	}

	@DisplayName("회원 목록을 조회한다.")
	@Test
	void findAll() {
		memberRepository.save(member("memberA").toEntity());
		memberRepository.save(member("memberB").toEntity());
		List<MemberResponseDTO> members = memberService.findAll();
		assertThat(members).hasSize(2);
	}

	private MemberRequestDTO member(String name) {
		return MemberRequestDTO.builder()
			.name(name)
			.build();
	}
}
