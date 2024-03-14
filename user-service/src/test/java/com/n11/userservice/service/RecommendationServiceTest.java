package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.common.RestResponse;
import com.n11.userservice.dto.LocationDTO;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.Recommendation;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class RecommendationServiceTest {
    @Mock
    private RestaurantClient restaurantClient;

    @Mock
    private UserReviewEntityService userReviewEntityService;

    @InjectMocks
    private RecommendationService recommendationServiceUnderTest;

    @Test
    void shouldReturnScoreWhenAverageScoreIsGreaterThanZero() {
        double averageUserScore = 3.5;
        double distance = 1;
        double expectedScore = (averageUserScore * 0.7) + (1.0 / (distance + 1) * 0.3);

        double actualScore = RecommendationService.calculateWeightedScore(averageUserScore, distance);

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnScoreWhenAverageScoreIsGreaterThanZeroAndDistanceIsGreaterThanZero() {
        double averageUserScore = 3.5;
        double distance = 1;
        double expectedScore = (averageUserScore * 0.7) + (1.0 / (distance + 1) * 0.3);

        double actualScore = RecommendationService.calculateWeightedScore(averageUserScore, distance);

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnScoreWhenAverageScoreIsZeroAndDistanceIsGreaterThanZero() {
        double averageUserScore = 0;
        double distance = 1;
        double expectedScore = (averageUserScore * 0.7) + (1.0 / (distance + 1) * 0.3);

        double actualScore = RecommendationService.calculateWeightedScore(averageUserScore, distance);

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnScoreWhenAverageScoreIsGreaterThanZeroAndDistanceIsZero() {
        double averageUserScore = 3.5;
        double distance = 0;
        double expectedScore = (averageUserScore * 0.7) + (1.0 / (distance + 1) * 0.3);

        double actualScore = RecommendationService.calculateWeightedScore(averageUserScore, distance);

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void shouldReturnScoreWhenAverageScoreIsZeroAndDistanceIsZero() {
        double averageUserScore = 0;
        double distance = 0;
        double expectedScore = (averageUserScore * 0.7) + (1.0 / (distance + 1) * 0.3);

        double actualScore = RecommendationService.calculateWeightedScore(averageUserScore, distance);

        assertEquals(expectedScore, actualScore);
    }

    @Test
    void shouldThrowExceptionWhenAverageScoreIsNegative() {
        double averageUserScore = -1;
        double distance = 1;

        assertThrows(IllegalArgumentException.class, () -> {
            RecommendationService.calculateWeightedScore(averageUserScore, distance);
        });
    }

    @Test
    void shouldThrowExceptionWhenDistanceIsNegative() {
        double averageUserScore = 3.5;
        double distance = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            RecommendationService.calculateWeightedScore(averageUserScore, distance);
        });
    }

    @Test
    void shouldCreateRecommendationInstanceWhenAverageScoreIsGreaterThanZero() {
        // given
        RestaurantDTO restaurant = new RestaurantDTO(
                "1",
                "Restaurant A",
                "Type A",
                new LocationDTO(40.7128, -74.006),
                1.0f
        );

        double averageUserScore = 3.5;

        // when
        Recommendation recommendation = RecommendationService.createRecommendationInstance(restaurant, averageUserScore);

        // then
        assertEquals(restaurant.id(), recommendation.getId());
        assertEquals(restaurant.name(), recommendation.getName());
        assertEquals(restaurant.type(), recommendation.getType());
        assertEquals(restaurant.location(), recommendation.getLocation());
        assertEquals(restaurant.distance(), recommendation.getDistance());
        assertEquals(averageUserScore, recommendation.getUserScore());
    }

    @Test
    void shouldCreateRecommendationInstanceWhenAverageScoreIsZero() {
        // given
        RestaurantDTO restaurant = new RestaurantDTO(
                "2",
                "Restaurant B",
                "Type B",
                new LocationDTO(40.7128, -74.006),
                1.0f
        );

        double averageUserScore = 0;

        // when
        Recommendation recommendation = RecommendationService.createRecommendationInstance(restaurant, averageUserScore);

        // then
        assertEquals(restaurant.id(), recommendation.getId());
        assertEquals(restaurant.name(), recommendation.getName());
        assertEquals(restaurant.type(), recommendation.getType());
        assertEquals(restaurant.location(), recommendation.getLocation());
        assertEquals(restaurant.distance(), recommendation.getDistance());
        assertEquals(averageUserScore, recommendation.getUserScore());
    }

    @Test
    void shouldThrowExceptionWhenAverageScoreIsNegative2() {
        // given
        RestaurantDTO restaurant = new RestaurantDTO(
                "3",
                "Restaurant C",
                "Type C",
                new LocationDTO(40.7128, -74.006),
                1.0f
        );

        double averageUserScore = -1;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            RecommendationService.createRecommendationInstance(restaurant, averageUserScore);
        });
    }


    @Test
    void shouldReturnEmptyListWhenNoNearbyRestaurants() {
        // given
        Double latitude = 40.7128;
        Double longitude = -74.006;
        Double distance = 10.0;
        when(restaurantClient.getNearbyRestaurants(latitude, longitude, distance)).thenReturn(RestResponse.of(List.of()));

        // when
        List<Recommendation> recommendations = recommendationServiceUnderTest.getRecommendedRestaurantsByLocation(latitude, longitude, distance);

        // then
        assertTrue(recommendations.isEmpty());
    }

    @Test
    void shouldReturnRecommendationsWhenNearbyRestaurantsExist() {
        // given
        Double latitude = 40.7128;
        Double longitude = -74.006;
        Double distance = 10.0;
        RestaurantDTO restaurant1 = new RestaurantDTO("1", "Restaurant 1", "Type 1", new LocationDTO(40.7128, -74.006), 1.0f);
        RestaurantDTO restaurant2 = new RestaurantDTO("2", "Restaurant 2", "Type 2", new LocationDTO(40.7128, -74.006), 1.0f);
        List<RestaurantDTO> nearbyRestaurants = List.of(restaurant1, restaurant2);
        when(restaurantClient.getNearbyRestaurants(latitude, longitude, distance)).thenReturn(RestResponse.of(nearbyRestaurants));

        UserReview review1 = new UserReview();
        review1.setRestaurantId("1");
        review1.setScore(ReviewScore.TWO);
        UserReview review2 = new UserReview();
        review2.setRestaurantId("2");
        review2.setScore(ReviewScore.THREE);

        when(userReviewEntityService.findByRestaurantId("1")).thenReturn(List.of(review1));
        when(userReviewEntityService.findByRestaurantId("2")).thenReturn(List.of(review2));

        // when
        List<Recommendation> recommendations = recommendationServiceUnderTest.getRecommendedRestaurantsByLocation(latitude, longitude, distance);

        // then
        assertFalse(recommendations.isEmpty());
        assertEquals(2, recommendations.size());
        assertEquals("Restaurant 2", recommendations.get(0).getName());
        assertEquals("Restaurant 1", recommendations.get(1).getName());
    }
}
