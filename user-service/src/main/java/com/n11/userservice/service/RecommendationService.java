package com.n11.userservice.service;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.entity.Recommendation;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.exceptions.ResourceNotFoundException;
import com.n11.userservice.service.entityservice.UserEntityService;
import com.n11.userservice.service.entityservice.UserReviewEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recommendations", cacheManager = "recommendationsCacheManager")
public class RecommendationService {
    private final UserEntityService userEntityService;
    private final UserReviewEntityService userReviewEntityService;
    private final RestaurantClient restaurantClient;

    protected static double calculateWeightedScore(double averageUserScore, double distance) {
        if (averageUserScore < 0) {
            throw new IllegalArgumentException("averageUserScore cannot be negative");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("distance cannot be negative");
        }
        // to avoid division by zero, we add 1 to the distance
        double distanceScore = 1.0 / (distance + 1);
        return averageUserScore * 0.7 + distanceScore * 0.3;
    }

    protected static Recommendation createRecommendationInstance(RestaurantDTO restaurant, double averageUserScore) {
        double weightedScore = calculateWeightedScore(averageUserScore, restaurant.distance());

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

    @Cacheable(key = "#userId + ';' + #distance", unless = "#result == null or #result.size() == 0")
    public List<Recommendation> getRecommendedRestaurantsByUserId(Long userId, Double distance) {
        Optional<User> user = userEntityService.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId.toString());
        }

        return getRecommendedRestaurantsByLocation(user.get().getLatitude(), user.get().getLongitude(), distance);
    }

    @Cacheable(key = "#latitude + ';' + #longitude + ';' + #distance", unless = "#result == null or #result.size() == 0")
    public List<Recommendation> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance) {
        if (distance == null) {
            distance = 10.0;
        }

        List<RestaurantDTO> restaurants = restaurantClient.getNearbyRestaurants(latitude, longitude, distance).getData();
        if (restaurants.isEmpty()) {
            return new ArrayList<>();
        }

        PriorityQueue<Recommendation> topRecommendations = new PriorityQueue<>(3, Comparator.comparingDouble(Recommendation::getWeightedScore));

        for (RestaurantDTO restaurant : restaurants) {
            List<UserReview> reviews = userReviewEntityService.findByRestaurantId(restaurant.id());

            double averageUserScore = reviews.stream().mapToDouble(UserReview::toNumericScore).average().orElse(0.0);

            Recommendation recommendation = createRecommendationInstance(restaurant, averageUserScore);

            if (topRecommendations.size() < 3) {
                topRecommendations.offer(recommendation);
            } else if (recommendation.getWeightedScore() > topRecommendations.peek().getWeightedScore()) {
                topRecommendations.poll();
                topRecommendations.offer(recommendation);
            }
        }

        return new ArrayList<>(topRecommendations);
    }
}
