package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.common.GeneralErrorMessage;
import com.n11.userservice.controller.contract.UserReviewControllerContract;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.exceptions.BadRequestException;
import com.n11.userservice.mapper.UserReviewMapper;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserReviewContractImpl implements UserReviewControllerContract {
    private final UserReviewEntityService userReviewEntityService;

    @Override
    public List<UserReviewDTO> getAllReviews(int page, int pageLimit, String sortBy, String sortDir) {
        Page<UserReview> reviews = userReviewEntityService.findAll(page, pageLimit, sortBy, sortDir);
        return UserReviewMapper.INSTANCE.convertToUserReviewDTOList(reviews);
    }

    @Override
    public UserReviewDTO createUserReview(CreateUserReviewRequest request) {
        UserReview review = UserReviewMapper.INSTANCE.convertToUserReview(request);
        review = userReviewEntityService.saveWithUserControl(review);
        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(review);
    }

    @Override
    public UserReviewDTO getUserReview(Long id) {
        UserReview review = userReviewEntityService.findByIdWithControl(id);
        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(review);
    }

    @Override
    public UserReviewDTO updateReviewComment(Long id, UpdateReviewCommentRequest request) {
        UserReview review = userReviewEntityService.findByIdWithControl(id);
        review.setComment(request.comment());
        review = userReviewEntityService.save(review);
        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(review);
    }

    @Override
    public UserReviewDTO updateReviewScore(Long id, UpdateReviewScoreRequest request) {
        UserReview review = userReviewEntityService.findByIdWithControl(id);
        review.setScore(request.score());
        review = userReviewEntityService.save(review);
        return UserReviewMapper.INSTANCE.convertToUserReviewDTO(review);
    }

    @Override
    public void deleteUserReview(Long id) {
        userReviewEntityService.delete(id);
    }
}
