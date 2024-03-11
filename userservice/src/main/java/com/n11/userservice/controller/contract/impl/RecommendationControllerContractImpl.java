package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.RecommendationControllerContract;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.dto.RestaurantDTO;
import com.n11.userservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationControllerContractImpl implements RecommendationControllerContract {
    private final RecommendationService recommendationService;
    @Override
    public List<RecommendationDTO> getRecommendedRestaurantsByUserId(Long userId, Double distance) {
        return recommendationService.getRecommendedRestaurantsByUserId(userId, distance);
    }

    @Override
    public List<RecommendationDTO> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance) {
        return recommendationService.getRecommendedRestaurantsByLocation(latitude, longitude, distance);
    }
}
