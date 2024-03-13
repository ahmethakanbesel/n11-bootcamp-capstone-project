package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.data.UserReviewData;
import com.n11.userservice.dto.UserReviewDTO;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserReviewControllerContractImplTest {
    @Mock
    private UserReviewEntityService userReviewEntityService;

    @InjectMocks
    private UserReviewControllerContractImpl userReviewControllerContractImpl;

    @Captor
    private ArgumentCaptor<UserReview> userReviewArgumentCaptor;

    @Test
    void shouldReturnAllReviews() {
        // given
        int page = 0;
        int size = 10;
        String sort = "id";
        String direction = "asc";

        List<UserReview> expectedReviews = UserReviewData.reviews();
        Page<UserReview> expectedPage = new PageImpl<>(expectedReviews);

        // when
        Mockito.when(userReviewEntityService.findAll(page, size, sort, direction)).thenReturn(expectedPage);
        List<UserReviewDTO> users = userReviewControllerContractImpl.getAllReviews(page, size, sort, direction);

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findAll(page, size, sort, direction);
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expectedReviews.size(), users.size());

        for (int i = 0; i < expectedReviews.size(); i++) {
            UserReview expected = expectedReviews.get(i);
            UserReviewDTO actual = users.get(i);

            assertEquals(expected.getId(), actual.id());
            assertEquals(expected.getUserId(), actual.userId());
            assertEquals(expected.getRestaurantId(), actual.restaurantId());
            assertEquals(expected.getComment(), actual.comment());
            assertEquals(expected.getScore(), actual.score());
        }
    }

    @Test
    void shouldCreateUserReview() {
        // given
        UserReview expected = UserReviewData.review();

        // when
        Mockito.when(userReviewEntityService.saveWithUserControl(ArgumentMatchers.any(UserReview.class))).thenReturn(expected);

        CreateUserReviewRequest request = new CreateUserReviewRequest(
                expected.getUserId(),
                expected.getRestaurantId(),
                expected.getScore(),
                expected.getComment()
        );
        UserReviewDTO userReview = userReviewControllerContractImpl.createUserReview(request);

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).saveWithUserControl(ArgumentMatchers.any(UserReview.class));
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expected.getId(), userReview.id());
        assertEquals(expected.getUserId(), userReview.userId());
        assertEquals(expected.getRestaurantId(), userReview.restaurantId());
        assertEquals(expected.getComment(), userReview.comment());
    }

    @Test
    void shouldUpdateReviewScore() {
        // given
        UserReview expected = UserReviewData.review();
        expected.setScore(ReviewScore.FOUR);

        // when
        Mockito.when(userReviewEntityService.findByIdWithControl(expected.getId())).thenReturn(expected);
        Mockito.when(userReviewEntityService.save(ArgumentMatchers.any(UserReview.class))).thenReturn(expected);

        UserReviewDTO userReview = userReviewControllerContractImpl.updateReviewScore(expected.getId(), new UpdateReviewScoreRequest(expected.getScore()));

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(expected.getId());
        inOrder.verify(userReviewEntityService).save(ArgumentMatchers.any(UserReview.class));
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expected.getId(), userReview.id());
        assertEquals(expected.getUserId(), userReview.userId());
        assertEquals(expected.getRestaurantId(), userReview.restaurantId());
        assertEquals(expected.getComment(), userReview.comment());
        assertEquals(expected.getScore(), userReview.score());
    }

    @Test
    void shouldUpdateReviewComment() {
        // given
        UserReview expected = UserReviewData.review();
        expected.setComment("new comment");

        // when
        Mockito.when(userReviewEntityService.findByIdWithControl(expected.getId())).thenReturn(expected);
        Mockito.when(userReviewEntityService.save(ArgumentMatchers.any(UserReview.class))).thenReturn(expected);

        UserReviewDTO userReview = userReviewControllerContractImpl.updateReviewComment(expected.getId(), new UpdateReviewCommentRequest(expected.getComment()));

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(expected.getId());
        inOrder.verify(userReviewEntityService).save(ArgumentMatchers.any(UserReview.class));
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expected.getId(), userReview.id());
        assertEquals(expected.getUserId(), userReview.userId());
        assertEquals(expected.getRestaurantId(), userReview.restaurantId());
        assertEquals(expected.getComment(), userReview.comment());
        assertEquals(expected.getScore(), userReview.score());
    }

    @Test
    void shouldReturnUserReview() {
        // given
        UserReview expected = UserReviewData.review();

        // when
        Mockito.when(userReviewEntityService.findByIdWithControl(expected.getId())).thenReturn(expected);

        UserReviewDTO userReview = userReviewControllerContractImpl.getUserReview(expected.getId());

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).findByIdWithControl(expected.getId());
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expected.getId(), userReview.id());
        assertEquals(expected.getUserId(), userReview.userId());
        assertEquals(expected.getRestaurantId(), userReview.restaurantId());
        assertEquals(expected.getComment(), userReview.comment());
        assertEquals(expected.getScore(), userReview.score());
    }

    @Test
    void shouldDeleteUserReview() {
        // given
        Long id = 100L;

        // when
        userReviewControllerContractImpl.deleteUserReview(id);

        InOrder inOrder = Mockito.inOrder(userReviewEntityService);
        inOrder.verify(userReviewEntityService).delete(id);
        inOrder.verifyNoMoreInteractions();
    }
}
