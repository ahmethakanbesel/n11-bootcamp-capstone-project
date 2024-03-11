package com.n11.userservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateReviewCommentRequest(
        @NotBlank(message = "Comment is mandatory")
        @Size(min = 5, max = 255, message = "Comment must be between 5 and 255 characters")
        @Schema(description = "Review comment", example = "This is a good restaurant")
        String comment
) {
}
