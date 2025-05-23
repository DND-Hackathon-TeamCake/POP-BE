package com.cake.pop.domain.letter.exception;

import com.cake.pop.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LetterErrorCode implements ErrorCode {

    LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "쪽지를 찾을 수 없습니다"),
    MAILBOX_NOT_FOUND(HttpStatus.NOT_FOUND, "우체통을 찾을 수 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
