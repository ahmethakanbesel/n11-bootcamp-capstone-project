package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews() {
        List<UserReviewDTO> reviews = userReviewControllerContract.getAllReviews(0, 10, "id", "asc");
        return ResponseEntity.ok(RestResponse.of(reviews));
    }

    @PostMapping
    @Operation(summary = "Create a review")
    public ResponseEntity<RestResponse<UserReviewDTO>> createReview(@Valid @RequestBody CreateUserReviewRequest request) {
        UserReviewDTO review = userReviewControllerContract.createUserReview(request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/comment")
    @Operation(summary = "Update a review's comment")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewComment(
            @Positive @PathVariable Long id,
            @Valid @RequestBody UpdateReviewCommentRequest request
    ) {
        UserReviewDTO review = userReviewControllerContract.updateReviewComment(id, request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/score")
    @Operation(summary = "Update a review's score")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewScore(
            @Positive @PathVariable Long id,
            @Valid @RequestBody UpdateReviewScoreRequest request
    ) {
        UserReviewDTO review = userReviewControllerContract.updateReviewScore(id, request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<RestResponse<Void>> deleteReview(@Positive @PathVariable Long id) {
        userReviewControllerContract.deleteUserReview(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by id")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReview(@Positive @PathVariable Long id) {
        UserReviewDTO review = userReviewControllerContract.getUserReview(id);
        return ResponseEntity.ok(RestResponse.of(review));
    }
}
