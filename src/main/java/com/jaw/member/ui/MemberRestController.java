package com.jaw.member.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.member.application.MemberService;

import lombok.RequiredArgsConstructor;

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
}
