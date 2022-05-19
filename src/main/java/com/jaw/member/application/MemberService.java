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
		return createResponseFromEntity(member);
	}

	public List<MemberResponseDTO> findAll() {
		return memberRepository.findAll()
			.stream()
			.map(this::createResponseFromEntity)
			.collect(Collectors.toList());
	}

	private MemberResponseDTO createResponseFromEntity(Member member) {
		return MemberResponseDTO.builder()
			.id(member.getId())
			.name(member.getName())
			.nickname(member.getNickname())
			.birthDate(member.getBirthDate())
			.email(member.getEmail())
			.phoneNumber(member.getPhoneNumber())
			.build();
	}
}
