package com.cake.pop.global.auth.handler;


import org.springframework.http.HttpStatus;

import com.cake.pop.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
	UNAUTHORIZED_USER(HttpStatus.NOT_FOUND, "비인가 사용자 요청입니다."),
	FORBIDDEN_USER(HttpStatus.NOT_FOUND, "ROLE_USER 권한이 필요합니다."),
	FORBIDDEN_GUEST(HttpStatus.NOT_FOUND, "ROLE_GUEST 권한이 필요합니다."),
	FORBIDDEN_MISMATCH(HttpStatus.NOT_FOUND, "어떤 권한도 매치되지 않습니다.");
	private final HttpStatus httpStatus;
	private final String message;
}
