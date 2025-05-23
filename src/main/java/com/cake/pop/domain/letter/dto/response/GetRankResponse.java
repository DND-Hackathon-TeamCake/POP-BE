package com.cake.pop.domain.letter.dto.response;

public record GetRankResponse(
        String region,
        Integer letterCount
) {
}
