package com.n11.userservice.data;

import com.n11.userservice.dto.LocationDTO;
import com.n11.userservice.entity.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class RecommendationData {
    public static List<Recommendation> recommendations() {
        List<Recommendation> expectedRecommendations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Recommendation recommendation = new Recommendation();
            recommendation.setId(String.format("%d", i));
            recommendation.setName("Restaurant " + i);
            recommendation.setType("Type " + i);
            LocationDTO location = new LocationDTO((double) i, 2.0 * i);
            recommendation.setLocation(location);
            recommendation.setDistance((float) i);
            recommendation.setUserScore((double) i);
            recommendation.setWeightedScore((double) i);
            expectedRecommendations.add(recommendation);
        }
        return expectedRecommendations;
    }
}
