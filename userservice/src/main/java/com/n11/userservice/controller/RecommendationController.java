package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.RecommendationControllerContract;
import com.n11.userservice.dto.RecommendationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recommendations", description = "The Recommendation API")
public class RecommendationController {

    private final RecommendationControllerContract recommendationControllerContract;

    @GetMapping("/with-user")
    @Operation(summary = "Get recommended restaurants by user id")
    public ResponseEntity<RestResponse<List<RecommendationDTO>>> getRecommendationsWithUser(
            @RequestParam("userId")
            @Positive
            @Schema(description = "User id", example = "100")
            Long userId,
            @RequestParam("distance")
            @NotNull
            @DecimalMin("0.1")
            @DecimalMax("10.0")
            @Schema(description = "Distance in kilometers", example = "5.0", type = "number")
            Double distance
    ) {
        List<RecommendationDTO> restaurants = recommendationControllerContract.getRecommendedRestaurantsByUserId(userId, distance);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }

    @GetMapping("/with-location")
    @Operation(summary = "Get recommended restaurants by location")
    public ResponseEntity<RestResponse<List<RecommendationDTO>>> getRecommendationsWithLocation(
            @RequestParam("latitude")
            @NotNull @DecimalMin("-90.0")
            @DecimalMax("90.0")
            @Schema(description = "Latitude", example = "39.925533", type = "number")
            Double latitude,
            @RequestParam("longitude")
            @NotNull
            @DecimalMin("-180.0")
            @Schema(description = "Longitude", example = "32.866287", type = "number")
            @DecimalMax("180.0")
            Double longitude,
            @RequestParam("distance")
            @NotNull
            @DecimalMin("0.1")
            @DecimalMax("10.0")
            @Schema(description = "Distance in kilometers", example = "5.0", type = "number")
            Double distance
    ) {
        List<RecommendationDTO> restaurants = recommendationControllerContract.getRecommendedRestaurantsByLocation(latitude, longitude, distance);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }
}
