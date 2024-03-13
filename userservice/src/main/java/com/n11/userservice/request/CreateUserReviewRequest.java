package com.n11.userservice.request;

import com.n11.userservice.enums.ReviewScore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record CreateUserReviewRequest(
        @NotNull(message = "User id is mandatory")
        @Range(min = 100)
        @Schema(description = "User id", example = "100")
        Long userId,

        @NotNull(message = "Restaurant id is mandatory")
        @Size(min = 1, max = 255, message = "Restaurant id must be between 1 and 255 characters long")
        @Schema(description = "Restaurant id", example = "restaurant-1")
        String restaurantId,

        @NotNull(message = "Score is mandatory")
        @Schema(description = "Review score", example = "FOUR")
        ReviewScore score,

        @Size(max = 255, message = "Comment must be at most 255 characters long")
        @Schema(description = "Review comment", example = "Great restaurant!")
        String comment
) {
}
