package com.cake.pop.domain.letter.dto.request;

import com.cake.pop.entity.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLetterRequest(
        @NotBlank
        @Size(min = 1, max = 350, message = "내용은 최소 1자 최대 350자 입니다.")
        String content,
        String imageUrl,
        @NotNull
        Region region
) {
}
