package com.n11.userservice.request;

import com.n11.userservice.enums.ReviewScore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewScoreRequest(
        @NotNull(message = "Score is mandatory")
        @Schema(description = "Review score", example = "FIVE")
        ReviewScore score
) {
}
