package com.cake.pop.domain.letter.dto.response;

public record GetLetterResponse(
        Long letterId,
        String content,
        String imageUrl,
        String region
) {
}
