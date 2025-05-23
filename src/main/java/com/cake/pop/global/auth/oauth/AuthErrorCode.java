package com.cake.pop.global.auth.oauth;

import org.springframework.http.HttpStatus;

import com.cake.pop.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

	UNSUPPORTED_SOCIAL_LOGIN(HttpStatus.NOT_FOUND, "해당 소셜 로그인은 지원되지 않습니다."),
	NOT_FOUND_PROVIDER(HttpStatus.NOT_FOUND, "알맞은 Provider를 찾을 수 없습니다."),
	NOT_FOUND_AUTH(HttpStatus.NOT_FOUND, "회원의 AUTH를 찾을 수 없습니다."),
	UNAUTHORIZED_TOKEN(HttpStatus.NOT_FOUND, "잘못된 토큰입니다."),
	FAIL_REISSUE_TOKEN(HttpStatus.NOT_FOUND, "토큰 재발급에 실패했습니다."),
	MISSING_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "AT가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
