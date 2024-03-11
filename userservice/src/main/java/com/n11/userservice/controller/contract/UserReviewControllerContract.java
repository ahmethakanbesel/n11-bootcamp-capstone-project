package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;

import java.util.List;

public interface UserReviewControllerContract {
    List<UserReviewDTO> getAllReviews(int page, int pageLimit, String sortBy, String sortDir);

    UserReviewDTO createUserReview(CreateUserReviewRequest request);

    UserReviewDTO getUserReview(Long id);

    UserReviewDTO updateReviewComment(Long id, UpdateReviewCommentRequest request);

    UserReviewDTO updateReviewScore(Long id, UpdateReviewScoreRequest request);

    void deleteUserReview(Long id);
}
