package com.cake.pop.global.auth.jwt;

import org.springframework.http.HttpStatus;

import com.cake.pop.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCode {

	MALFORMED_TOKEN(HttpStatus.NOT_FOUND, "알맞지 않은 형식의 토큰입니다."),
	INVALID_TOKEN(HttpStatus.NOT_FOUND, "유효하지 않은 토큰입니다."),
	EXPIRED_TOKEN(HttpStatus.NOT_FOUND, "만료된 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String message;

}