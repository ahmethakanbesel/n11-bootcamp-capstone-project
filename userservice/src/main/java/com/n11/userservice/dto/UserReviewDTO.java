package com.n11.userservice.dto;

import com.n11.userservice.enums.ReviewScore;

public record UserReviewDTO(
        Long id,
        Long userId,
        Long restaurantId,
        ReviewScore score,
        String review
) {
}
