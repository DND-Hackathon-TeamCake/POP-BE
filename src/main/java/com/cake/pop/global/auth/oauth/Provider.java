package com.cake.pop.global.auth.oauth;

import java.util.Arrays;

import com.cake.pop.global.exception.RestApiException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

	KAKAO("kakao"),
	NAVER("naver");

	private final String label;

	public static Provider fromProviderName(String providerName) {
		return Arrays.stream(values())
			.filter(provider -> provider.getLabel().equalsIgnoreCase(providerName))
			.findFirst()
			.orElseThrow(() -> new RestApiException(AuthErrorCode.NOT_FOUND_PROVIDER));
	}

	public static Provider fromSocialEmail(String socialEmail) {
		return Arrays.stream(values())
			.filter(provider -> socialEmail.contains(provider.getLabel()))
			.findFirst()
			.orElseThrow(() -> new RestApiException(AuthErrorCode.NOT_FOUND_PROVIDER));
	}
}
