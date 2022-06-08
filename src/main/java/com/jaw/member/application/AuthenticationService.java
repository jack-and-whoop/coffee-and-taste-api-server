package com.jaw.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaw.auth.JwtUtil;
import com.jaw.member.domain.Member;
import com.jaw.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	public String login(String email, String password) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(IllegalArgumentException::new);

		if (!member.authenticate(password, passwordEncoder)) {
			throw new IllegalArgumentException();
		}

		return jwtUtil.encode(member.getId());
	}

	public Long parseToken(String accessToken) {
		return jwtUtil.decode(accessToken);
	}
}
