package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.RecommendationControllerContract;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.entity.Recommendation;
import com.n11.userservice.mapper.RecommendationMapper;
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
        List<Recommendation> recommendations = recommendationService.getRecommendedRestaurantsByUserId(userId, distance);
        return RecommendationMapper.INSTANCE.convertToRecommendationDTOList(recommendations);
    }

    @Override
    public List<RecommendationDTO> getRecommendedRestaurantsByLocation(Double latitude, Double longitude, Double distance) {
        List<Recommendation> recommendations = recommendationService.getRecommendedRestaurantsByLocation(latitude, longitude, distance);
        return RecommendationMapper.INSTANCE.convertToRecommendationDTOList(recommendations);
    }
}
