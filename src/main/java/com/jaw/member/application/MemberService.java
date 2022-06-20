package com.jaw.member.application;

import com.jaw.exception.MemberNotFoundException;
import com.jaw.exception.UserEmailDuplicationException;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;
import com.jaw.member.ui.MemberRequestDTO;
import com.jaw.member.ui.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberResponseDTO create(MemberRequestDTO request) {
		String email = request.getEmail();
		if (memberRepository.findByEmail(email).isPresent()) {
			throw new UserEmailDuplicationException(email);
		}

		request.setPassword(passwordEncoder.encode(request.getPassword()));
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
			.orElseThrow(() -> new MemberNotFoundException(email));
	}
}
