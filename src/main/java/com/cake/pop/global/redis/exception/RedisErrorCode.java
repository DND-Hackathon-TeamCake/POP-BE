package com.cake.pop.global.redis.exception;

import org.springframework.http.HttpStatus;

import com.cake.pop.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisErrorCode implements ErrorCode {

	REDIS_SAVE_ERROR(HttpStatus.NOT_FOUND, "저장하지 못했습니다."),
	REDIS_FIND_ERROR(HttpStatus.NOT_FOUND, "값을 찾는 도중 오류가 발생했습니다."),
	REDIS_DELETE_ERROR(HttpStatus.NOT_FOUND, "삭제하지 못했습니다."),
	REDIS_EXPIRE_ERROR(HttpStatus.NOT_FOUND, "만료시키지하지 못했습니다."),
	REDIS_EXPIRED_ERROR(HttpStatus.NOT_FOUND, "만료된 키 입니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
