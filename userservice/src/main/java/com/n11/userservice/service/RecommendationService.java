package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.Recommendation;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.exceptions.ResourceNotFoundException;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recommendations", cacheManager = "recommendationsCacheManager")
public class RecommendationService {
    private final UserEntityService userEntityService;
    private final UserReviewEntityService userReviewEntityService;
    private final RestaurantClient restaurantClient;

    private static Recommendation createRecommendationInstance(RestaurantDTO restaurant, double averageUserScore) {
        // to avoid division by zero, we add 1 to the distance
        double distanceScore = 1.0 / (restaurant.distance() + 1);
        double weightedScore = averageUserScore * 0.7 + distanceScore * 0.3;

        Recommendation recommendation = new Recommendation();
        recommendation.setId(restaurant.id());
        recommendation.setName(restaurant.name());
        recommendation.setType(restaurant.type());
        recommendation.setLocation(restaurant.location());
        recommendation.setDistance(restaurant.distance());
        recommendation.setUserScore(averageUserScore);
        recommendation.setWeightedScore(weightedScore);

        return recommendation;
    }

    @Cacheable(value = "recommendations", key = "#userId + ';' + #distance", unless = "#result == null or #result.size() == 0")
    public List<Recommendation> getRecommendedRestaurantsByUserId(Long userId, Double distance) {
        Optional<User> user = userEntityService.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId.toString());
        }

        return getRecommendedRestaurantsByLocation(user.get().getLatitude(), user.get().getLongitude(), distance);
    }

    @Cacheable(value = "recommendations", key = "#latitude + ';' + #longitude + ';' + #distance", unless = "#result == null or #result.size() == 0")
    public List<Recommendation> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance) {
        if (distance == null) {
            distance = 10.0;
        }

        List<RestaurantDTO> restaurants = restaurantClient.getNearbyRestaurants(latitude, longitude, distance).getData();
        if (restaurants.isEmpty()) {
            return new ArrayList<>();
        }

        List<Recommendation> recommendations = new ArrayList<>();

        for (RestaurantDTO restaurant : restaurants) {
            List<UserReview> reviews = userReviewEntityService.findByRestaurantId(restaurant.id());

            // NOTE: should we take into account the user reviews if there are no reviews?
            double averageUserScore = reviews.stream().mapToDouble(UserReview::toNumericScore).average().orElse(0.0);

            Recommendation recommendation = createRecommendationInstance(restaurant, averageUserScore);
            recommendations.add(recommendation);
        }

        // return sorted recommendations
        recommendations.sort((r1, r2) -> Double.compare(r2.getWeightedScore(), r1.getWeightedScore()));
        return recommendations;
    }
}
