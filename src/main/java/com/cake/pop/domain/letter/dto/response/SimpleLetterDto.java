package com.cake.pop.domain.letter.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record SimpleLetterDto(
        Long letterId,
        String content,
        @JsonFormat(pattern = "yy.MM.dd")
        LocalDate createdAt
) {
}
