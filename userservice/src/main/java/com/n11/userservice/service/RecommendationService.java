package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.exceptions.ResourceNotFoundException;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserEntityService userEntityService;
    private final UserReviewEntityService userReviewEntityService;
    private final RestaurantClient restaurantClient;

    public List<RecommendationDTO> getRecommendedRestaurantsByUserId(Long userId, Double distance) {
        Optional<User> user = userEntityService.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return getRecommendedRestaurantsByLocation(user.get().getLatitude(), user.get().getLongitude(), distance);
    }

    public List<RecommendationDTO> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance) {
        if (distance == null) {
            distance = 10.0;
        }

        List<RestaurantDTO> restaurants = restaurantClient.getNearbyRestaurants(latitude, longitude, distance).getData();
        if (restaurants.isEmpty()) {
            return new ArrayList<>();
        }

        List<RecommendationDTO> recommendations = new ArrayList<>();

        for (RestaurantDTO restaurant : restaurants) {
            List<UserReview> reviews = userReviewEntityService.findByRestaurantId(restaurant.id());

            // TODO: should we take into account the user reviews if there are no reviews?
            double averageUserScore = reviews.stream().mapToDouble(UserReview::toNumericScore).average().orElse(0.0);

            RecommendationDTO recommendation = getRecommendationDTO(restaurant, averageUserScore);
            recommendations.add(recommendation);
        }

        // return sorted recommendations
        recommendations.sort((r1, r2) -> Double.compare(r2.weightedScore(), r1.weightedScore()));
        return recommendations;
    }

    private static RecommendationDTO getRecommendationDTO(RestaurantDTO restaurant, double averageUserScore) {
        // to avoid division by zero, we add 1 to the distance
        double distanceScore = 1.0 / (restaurant.distance() + 1);
        double weightedScore = averageUserScore * 0.7 + distanceScore * 0.3;

        RecommendationDTO recommendation = new RecommendationDTO(
                restaurant.id(),
                restaurant.name(),
                restaurant.type(),
                restaurant.location(),
                restaurant.distance(),
                averageUserScore,
                weightedScore
        );

        return recommendation;
    }
}