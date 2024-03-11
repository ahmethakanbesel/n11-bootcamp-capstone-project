package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.request.CreateUserReviewRequest;

import java.util.List;

public interface UserReviewControllerContract {
    List<UserReviewDTO> getAllReviews(int page, int pageLimit, String sortBy, String sortDir);
    UserReviewDTO createUserReview(CreateUserReviewRequest request);

    UserReviewDTO getUserReview(Long id);

    UserReviewDTO updateReviewComment(Long id, String comment);

    UserReviewDTO updateReviewScore(Long id, ReviewScore score);

    void deleteUserReview(Long id);
}
