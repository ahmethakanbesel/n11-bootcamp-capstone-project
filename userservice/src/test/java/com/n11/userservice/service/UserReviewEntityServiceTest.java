package com.n11.userservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.exceptions.BadRequestException;
import com.n11.userservice.repository.UserReviewRepository;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserReviewEntityServiceTest {

    @Mock
    private UserReviewRepository userReviewRepository;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private UserReviewEntityService userReviewEntityService;

    @Test
    void shouldFindByRestaurantId() {
        // given
        String restaurantId = "1";
        List<UserReview> expectedUserReviews = new ArrayList<>();
        UserReview userReview1 = new UserReview();
        userReview1.setUserId(1L);
        userReview1.setRestaurantId("1");
        userReview1.setComment("Good food");
        userReview1.setScore(ReviewScore.FOUR);
        expectedUserReviews.add(userReview1);

        UserReview userReview2 = new UserReview();
        userReview2.setUserId(2L);
        userReview2.setRestaurantId("1");
        userReview2.setComment("Excellent service");
        userReview2.setScore(ReviewScore.FIVE);
        expectedUserReviews.add(userReview2);

        when(userReviewRepository.findByRestaurantId(restaurantId)).thenReturn(expectedUserReviews);

        // when
        List<UserReview> actualUserReviews = userReviewEntityService.findByRestaurantId(restaurantId);

        // then
        assertEquals(expectedUserReviews.size(), actualUserReviews.size());
        assertEquals(expectedUserReviews, actualUserReviews);
    }

    @Test
    void shouldNotSaveUserReviewWithUserControlWhenUserIsEmpty() {
        // given
        UserReview userReview = new UserReview();
        userReview.setUserId(1L);
        userReview.setRestaurantId("1");
        userReview.setComment("Good food");
        userReview.setScore(ReviewScore.FOUR);

        when(userEntityService.findById(userReview.getUserId())).thenReturn(Optional.of(new User()));

        // when
        UserReview savedUserReview = userReviewEntityService.saveWithUserControl(userReview);

        // then
        assertNull(savedUserReview);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUserNotFound() {
        // given
        UserReview userReview = new UserReview();
        userReview.setUserId(1L);
        userReview.setRestaurantId("1");
        userReview.setComment("Good food");
        userReview.setScore(ReviewScore.FOUR);

        when(userEntityService.findById(userReview.getUserId())).thenReturn(Optional.empty());

        // then
        assertThrows(BadRequestException.class, () -> userReviewEntityService.saveWithUserControl(userReview));
    }
}
