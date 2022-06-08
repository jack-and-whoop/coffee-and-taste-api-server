package com.jaw.member.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponseDTO create(MemberRequestDTO request) {
		Member member = memberRepository.save(request.toEntity());
		return new MemberResponseDTO(member);
	}

	public List<MemberResponseDTO> findAll() {
		return memberRepository.findAll()
			.stream()
			.map(MemberResponseDTO::new)
			.collect(Collectors.toList());
	}

	public MemberResponseDTO findByEmail(String email) {
		return memberRepository.findByEmail(email)
			.map(MemberResponseDTO::new)
			.orElseThrow(IllegalArgumentException::new);
	}
}
