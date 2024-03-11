package com.n11.userservice.request;

import com.n11.userservice.enums.ReviewScore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record CreateUserReviewRequest(
        @NotNull(message = "User id is mandatory")
        @Range(min = 100)
        Long userId,

        @NotNull(message = "Restaurant id is mandatory")
        Long restaurantId,

        @NotNull(message = "Score is mandatory")
        ReviewScore score,

        @Size(max = 255, message = "Comment must be at most 255 characters long")
        String comment
) {
}
