package com.n11.userservice.dto;

import com.n11.userservice.enums.ReviewScore;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserReviewDTO(
        @Schema(description = "Review's id", example = "1")
        Long id,
        @Schema(description = "User's id", example = "100")
        Long userId,
        @Schema(description = "Restaurant's id", example = "03d3042f-fd88-4399-a29c-7988e3bf9d14")
        String restaurantId,
        @Schema(description = "Review's score", example = "FOUR")
        ReviewScore score,
        @Schema(description = "Review's comment", example = "Great restaurant!")
        String comment
) {
}
