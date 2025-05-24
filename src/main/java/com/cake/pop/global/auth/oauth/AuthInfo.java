package com.cake.pop.global.auth.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthInfo {

	private String email;

	@Builder
	private AuthInfo(String email) {
		this.email = email;
	}

	public static AuthInfo of(String email) {
		return AuthInfo.builder()
			.email(email)
			.build();
	}
}
