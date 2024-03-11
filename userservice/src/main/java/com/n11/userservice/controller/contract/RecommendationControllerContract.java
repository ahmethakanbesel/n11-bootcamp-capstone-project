package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.dto.RestaurantDTO;

import java.util.List;

public interface RecommendationControllerContract {
    List<RecommendationDTO> getRecommendedRestaurantsByUserId(Long userId, Double distance);

    List<RecommendationDTO> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance);
}
