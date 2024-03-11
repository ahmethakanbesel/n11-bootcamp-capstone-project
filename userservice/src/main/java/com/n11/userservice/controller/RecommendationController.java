package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.RecommendationControllerContract;
import com.n11.userservice.dto.RecommendationDTO;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecommendationController {

    private final RecommendationControllerContract recommendationControllerContract;

    @GetMapping("/with-user/{userId}")
    public ResponseEntity<RestResponse<List<RecommendationDTO>>> getRecommendationsWithUser(
            @Positive Long userId,
            @RequestParam Double distance
    ) {
        List<RecommendationDTO> restaurants = recommendationControllerContract.getRecommendedRestaurantsByUserId(userId, distance);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }

    @GetMapping("/with-location")
    public ResponseEntity<RestResponse<List<RecommendationDTO>>> getRecommendationsWithLocation(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double distance
    ) {
        List<RecommendationDTO> restaurants = recommendationControllerContract.getRecommendedRestaurantsByLocation(latitude, longitude, distance);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }
}
