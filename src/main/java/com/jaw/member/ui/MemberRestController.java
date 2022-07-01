package com.jaw.member.ui;

import com.jaw.auth.UserAuthentication;
import com.jaw.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberRestController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<MemberResponseDTO> create(@RequestBody MemberRequestDTO request) {
		MemberResponseDTO response = memberService.create(request);
		return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
			.body(response);
	}

	@GetMapping
	public ResponseEntity<List<MemberResponseDTO>> findAll() {
		return ResponseEntity.ok(memberService.findAll());
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated() and hasAnyAuthority('USER')")
	public ResponseEntity<MemberResponseDTO> findById(UserAuthentication authentication) {
		return ResponseEntity.ok(memberService.findById(authentication.getUserId()));
	}
}
