package com.n11.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecommendationDTO(
        String id,
        @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant")
        String name,
        @Schema(description = "Restaurant's type", example = "TURKISH")
        String type,
        LocationDTO location,
        @Schema(description = "Distance from the user's/given location", example = "5.0")
        Float distance,

        @Schema(description = "User's score for the restaurant [0, 5]", example = "4.5")
        Double userScore,

        @Schema(description = "Weighted score for the restaurant (70% user reviews, 30% distance from the location)", example = "4.0")
        Double weightedScore
) {
}
