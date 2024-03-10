package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.common.RestResponse;
import com.n11.restaurantservice.controller.contract.RestaurantControllerContract;
import com.n11.restaurantservice.dto.RestaurantDTO;
import com.n11.restaurantservice.request.CreateRestaurantRequest;
import com.n11.restaurantservice.request.UpdateRestaurantRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurants", description = "The Restaurant API")
public class RestaurantController {
    private final RestaurantControllerContract restaurantControllerContract;

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRestaurants(
            @RequestParam(value = "page", defaultValue = "0")
            @Range(min = 0, max = 1000, message = "Page number must be between 0 and 1000")
            @Schema(description = "Page number", example = "0", minimum = "0", maximum = "1000")
            int page,
            @RequestParam(value = "size", defaultValue = "10")
            @Range(min = 1, max = 100, message = "Page size must be between 1 and 100")
            @Schema(description = "Page size", example = "10", minimum = "1", maximum = "100")
            int size,
            @RequestParam(value = "sortBy", defaultValue = "id")
            @Pattern(regexp = "^(id|name|type)$", message = "Sort by must be one of 'id', 'name', 'type'")
            @Schema(description = "Sort by", example = "id", allowableValues = {"id", "name", "type"})
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc")
            @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be one of 'asc', 'desc'")
            @Schema(description = "Sort direction", example = "asc", allowableValues = {"asc", "desc"})
            String sortDir
    ) {
        List<RestaurantDTO> restaurants = restaurantControllerContract.getAllRestaurants(page, size, sortBy, sortDir);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }

    @GetMapping("/nearby")
    @Operation(summary = "Get nearby restaurants")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getNearbyRestaurants(
            @RequestParam("latitude")
            @NotNull @DecimalMin("-90.0")
            @DecimalMax("90.0")
            @Schema(description = "Latitude for the restaurant's location", example = "40.7128", required = true)
            Double latitude,
            @RequestParam("longitude")
            @NotNull
            @DecimalMin("-180.0")
            @Schema(description = "Longitude for the restaurant's location", example = "-74.0060", required = true)
            @DecimalMax("180.0")
            Double longitude,
            @RequestParam("distance")
            @NotNull
            @DecimalMin("0.1")
            @DecimalMax("100.0")
            @Schema(description = "Distance in kilometers", example = "5.0", required = true)
            Double distance
    ) {
        List<RestaurantDTO> restaurants = restaurantControllerContract.getNearbyRestaurants(latitude, longitude, distance);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }

    @GetMapping("/with-name")
    @Operation(summary = "Get restaurants by name")
    public ResponseEntity<RestResponse<List<RestaurantDTO>>> getRestaurantsByName(
            @RequestParam("name")
            @NotBlank(message = "Name cannot be blank")
            @Size(min = 3, max = 63, message = "Name must be between 3 and 63 characters")
            @Schema(description = "Restaurant's name", example = "Ocakbaşı Restaurant", minLength = 3, maxLength = 63, required = true)
            String name
    ) {
        List<RestaurantDTO> restaurants = restaurantControllerContract.getRestaurantsByName(name);
        return ResponseEntity.ok(RestResponse.of(restaurants));
    }

    @PostMapping
    @Operation(summary = "Create a restaurant")
    public ResponseEntity<RestResponse<RestaurantDTO>> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        RestaurantDTO restaurant = restaurantControllerContract.createRestaurant(request);
        return ResponseEntity.ok(RestResponse.of(restaurant));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a restaurant by id")
    public ResponseEntity<RestResponse<RestaurantDTO>> getRestaurant(@NotNull @PathVariable String id) {
        RestaurantDTO restaurant = restaurantControllerContract.getRestaurant(id);
        return ResponseEntity.ok(RestResponse.of(restaurant));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a restaurant by id")
    public ResponseEntity<Void> deleteRestaurant(@NotNull @PathVariable String id) {
        restaurantControllerContract.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a restaurant by id")
    public ResponseEntity<RestResponse<RestaurantDTO>> updateRestaurant(@PathVariable String id, @RequestBody UpdateRestaurantRequest request) {
        RestaurantDTO restaurant = restaurantControllerContract.updateRestaurant(id, request);
        return ResponseEntity.ok(RestResponse.of(restaurant));
    }
}
