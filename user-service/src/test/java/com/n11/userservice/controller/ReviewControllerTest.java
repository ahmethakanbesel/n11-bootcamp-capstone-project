package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.UserServiceApplication;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.request.CreateUserReviewRequest;
import com.n11.userservice.request.UpdateReviewCommentRequest;
import com.n11.userservice.request.UpdateReviewScoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserServiceApplication.class)
class ReviewControllerTest extends BaseControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnAllReviews() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReview() throws Exception {
        CreateUserReviewRequest request = new CreateUserReviewRequest(
                100L,
                "restaurant-2",
                ReviewScore.FIVE,
                "This is a review"
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @ParameterizedTest
    @CsvSource({
            "100, restaurant-1, FIVE, 'This is a review'",
            "999, restaurant-1, FIVE, 'This is a review'",
            "100, '', FIVE, 'This is a review'"
    })
    void shouldNotCreateReviewForInvalidInputs(long userId, String restaurantId, ReviewScore score, String reviewText) throws Exception {
        CreateUserReviewRequest request = new CreateUserReviewRequest(
                userId,
                restaurantId,
                score,
                reviewText
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean success = isFailure(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotCreateReviewWhenScoreInvalid() throws Exception {
        CreateUserReviewRequest request = new CreateUserReviewRequest(
                100L,
                "restaurant-1",
                null,
                "This is a review"
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean success = isFailure(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReviewWhenCommentIsNotProvided() throws Exception {
        CreateUserReviewRequest request = new CreateUserReviewRequest(
                100L,
                "restaurant-99",
                ReviewScore.FIVE,
                null
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldUpdateScore() throws Exception {
        UpdateReviewScoreRequest request = new UpdateReviewScoreRequest(
                ReviewScore.FIVE
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/reviews/100/score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateScoreWhenScoreIsNull() throws Exception {
        UpdateReviewScoreRequest request = new UpdateReviewScoreRequest(
                null
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/reviews/100/score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean success = isFailure(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateScoreWhenReviewDoesNotExist() throws Exception {
        UpdateReviewScoreRequest request = new UpdateReviewScoreRequest(
                ReviewScore.FIVE
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/reviews/999/score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        boolean success = isFailure(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldUpdateComment() throws Exception {
        UpdateReviewCommentRequest request = new UpdateReviewCommentRequest(
                "This is a new comment"
        );

        String payload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/reviews/100/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateCommentWhenCommentIsNull() throws Exception {
        String payload = objectMapper.writeValueAsString(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/reviews/100/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean success = isFailure(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldReturnReviewById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldDeleteReviewById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/reviews/101"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }
}
