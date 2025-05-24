package com.cake.pop.global.auth.jwt;


import com.cake.pop.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class CustomJwtException extends RuntimeException {

	private final String code;

	public CustomJwtException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getHttpStatus().name();
	}
}
