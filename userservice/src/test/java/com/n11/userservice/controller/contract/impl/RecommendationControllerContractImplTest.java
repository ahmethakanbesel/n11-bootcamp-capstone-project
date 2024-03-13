package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.data.RecommendationData;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.entity.Recommendation;
import com.n11.userservice.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerContractImplTest {
    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private RecommendationControllerContractImpl recommendationControllerContractImpl;

    @Captor
    private ArgumentCaptor<RecommendationDTO> recommendationDTOArgumentCaptor;

    @Test
    void shouldReturnRecommendationsByUserId() {
        // given
        Long userId = 100L;
        Double distance = 10.0;

        // when
        List<Recommendation> expectedRecommendations = RecommendationData.recommendations();
        Mockito.when(recommendationService.getRecommendedRestaurantsByUserId(userId, distance)).thenReturn(expectedRecommendations);
        List<RecommendationDTO> recommendations = recommendationControllerContractImpl.getRecommendedRestaurantsByUserId(userId, distance);

        // then
        assertEquals(expectedRecommendations.size(), recommendations.size());

        for (int i = 0; i < expectedRecommendations.size(); i++) {
            Recommendation expectedRecommendation = expectedRecommendations.get(i);
            RecommendationDTO actualRecommendationDTO = recommendations.get(i);

            assertEquals(expectedRecommendation.getId(), actualRecommendationDTO.id());
            assertEquals(expectedRecommendation.getName(), actualRecommendationDTO.name());
            assertEquals(expectedRecommendation.getType(), actualRecommendationDTO.type());
            assertEquals(expectedRecommendation.getLocation().latitude(), actualRecommendationDTO.location().latitude());
            assertEquals(expectedRecommendation.getLocation().longitude(), actualRecommendationDTO.location().longitude());
            assertEquals(expectedRecommendation.getDistance(), actualRecommendationDTO.distance());
            assertEquals(expectedRecommendation.getUserScore(), actualRecommendationDTO.userScore());
            assertEquals(expectedRecommendation.getWeightedScore(), actualRecommendationDTO.weightedScore());
        }
    }

    @Test
    void shouldReturnRecommendationsByLocation() {
        // given
        Double latitude = 40.0;
        Double longitude = 30.0;
        Double distance = 10.0;

        // when
        List<Recommendation> expectedRecommendations = RecommendationData.recommendations();
        Mockito.when(recommendationService.getRecommendedRestaurantsByLocation(latitude, longitude, distance)).thenReturn(expectedRecommendations);
        List<RecommendationDTO> recommendations = recommendationControllerContractImpl.getRecommendedRestaurantsByLocation(latitude, longitude, distance);

        // then
        assertEquals(expectedRecommendations.size(), recommendations.size());

        for (int i = 0; i < expectedRecommendations.size(); i++) {
            Recommendation expectedRecommendation = expectedRecommendations.get(i);
            RecommendationDTO actualRecommendationDTO = recommendations.get(i);

            assertEquals(expectedRecommendation.getId(), actualRecommendationDTO.id());
            assertEquals(expectedRecommendation.getName(), actualRecommendationDTO.name());
            assertEquals(expectedRecommendation.getType(), actualRecommendationDTO.type());
            assertEquals(expectedRecommendation.getLocation().latitude(), actualRecommendationDTO.location().latitude());
            assertEquals(expectedRecommendation.getLocation().longitude(), actualRecommendationDTO.location().longitude());
            assertEquals(expectedRecommendation.getDistance(), actualRecommendationDTO.distance());
            assertEquals(expectedRecommendation.getUserScore(), actualRecommendationDTO.userScore());
            assertEquals(expectedRecommendation.getWeightedScore(), actualRecommendationDTO.weightedScore());
        }
    }

}
