package com.jaw.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jaw.member.application.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
		String accessToken = authenticationService.login(request.getEmail(), request.getPassword());
		return new LoginResponseDTO(accessToken);
	}
}
