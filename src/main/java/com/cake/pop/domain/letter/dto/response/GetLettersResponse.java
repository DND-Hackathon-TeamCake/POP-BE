package com.cake.pop.domain.letter.dto.response;

import java.util.List;

public record GetLettersResponse(
    String region,
    List<SimpleLetterDto> letters
) {
}
