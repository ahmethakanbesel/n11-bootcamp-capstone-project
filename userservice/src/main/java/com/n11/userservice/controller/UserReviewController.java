package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.common.RestResponse;
import com.n11.userservice.request.CreateUserReviewRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserReviewController {
    private final UserReviewControllerContract userReviewControllerContract;

    public UserReviewController(UserReviewControllerContract userReviewControllerContract) {
        this.userReviewControllerContract = userReviewControllerContract;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<UserReviewDTO>>> getAllReviews() {
        List<UserReviewDTO> reviews = userReviewControllerContract.getAllReviews(0, 10, "id", "asc");
        return ResponseEntity.ok(RestResponse.of(reviews));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserReviewDTO>> createReview(@Valid @RequestBody CreateUserReviewRequest request) {
        UserReviewDTO review = userReviewControllerContract.createUserReview(request);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/comment")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewComment(@PathVariable Long id, @RequestBody String comment) {
        UserReviewDTO review = userReviewControllerContract.updateReviewComment(id, comment);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @PatchMapping("/{id}/score")
    public ResponseEntity<RestResponse<UserReviewDTO>> updateReviewScore(@PathVariable Long id, @RequestBody ReviewScore score) {
        UserReviewDTO review = userReviewControllerContract.updateReviewScore(id, score);
        return ResponseEntity.ok(RestResponse.of(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteReview(@PathVariable Long id) {
        userReviewControllerContract.deleteUserReview(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserReviewDTO>> getReview(@PathVariable Long id) {
        UserReviewDTO review = userReviewControllerContract.getUserReview(id);
        return ResponseEntity.ok(RestResponse.of(review));
    }
}
