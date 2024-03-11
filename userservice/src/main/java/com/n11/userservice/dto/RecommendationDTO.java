package com.n11.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecommendationDTO(
        String id,
        @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant")
        String name,
        @Schema(description = "Restaurant's type", example = "TURKISH")
        String type,
        LocationDTO location,
        Float distance,
        Double userScore,
        Double weightedScore
) {
}
