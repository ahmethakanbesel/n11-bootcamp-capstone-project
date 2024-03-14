package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reviews", description = "The Review API")
public class UserReviewController {
    private final UserReviewControllerContract userReviewControllerContract;

    @GetMapping
    @Operation(summary = "Get all reviews")
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews(
            @RequestParam(value = "page", defaultValue = "0")
            @Range(min = 0, max = 1000, message = "Page number must be between 0 and 1000")
            @Schema(description = "Page number", example = "0", minimum = "0", maximum = "1000")
            int page,
            @RequestParam(value = "size", defaultValue = "10")
            @Range(min = 1, max = 100, message = "Page size must be between 1 and 100")
            @Schema(description = "Page size", example = "10", minimum = "1", maximum = "100")
            int size,
            @RequestParam(value = "sortBy", defaultValue = "id")
            @Pattern(regexp = "^(id|score|restaurantId)$", message = "Sort by must be one of 'id', 'score', 'restaurantId'")
            @Schema(description = "Sort by", example = "id", allowableValues = {"id", "score", "restaurantId"})
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc")
            @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be one of 'asc', 'desc'")
            @Schema(description = "Sort direction", example = "asc", allowableValues = {"asc", "desc"})
            String sortDir
    ) {
        List<UserReviewDTO> reviews = userReviewControllerContract.getAllReviews(page, size, sortBy, sortDir);
        return ResponseEntity.ok(RestResponse.of(reviews));
    }

    @PostMapping
    @Operation(summary = "Create a review")
    public ResponseEntity<RestResponse<UserReviewDTO>> createReview(
            @Valid @RequestBody CreateUserReviewRequest request
    ) {
        UserReviewDTO review = userReviewControllerContract.createUserReview(request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/comment")
    @Operation(summary = "Update a review's comment")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewComment(
            @Positive @PathVariable @Schema(description = "User id", example = "100", type = "number") Long id,
            @Valid @RequestBody UpdateReviewCommentRequest request
    ) {
        UserReviewDTO review = userReviewControllerContract.updateReviewComment(id, request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/score")
    @Operation(summary = "Update a review's score")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewScore(
            @Positive @PathVariable @Schema(description = "User id", example = "100", type = "number") Long id,
            @Valid @RequestBody UpdateReviewScoreRequest request
    ) {
        UserReviewDTO review = userReviewControllerContract.updateReviewScore(id, request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<RestResponse<Void>> deleteReview(
            @Positive
            @PathVariable
            @Schema(description = "User id", example = "100", type = "number")
            Long id
    ) {
        userReviewControllerContract.deleteUserReview(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by id")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReview(
            @Positive
            @PathVariable
            @Schema(description = "User id", example = "100", type = "number") Long id
    ) {
        UserReviewDTO review = userReviewControllerContract.getUserReview(id);
        return ResponseEntity.ok(RestResponse.of(review));
    }
}
