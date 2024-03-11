package com.n11.userservice.config;

import com.n11.userservice.client.RestaurantClient;
import com.n11.userservice.common.RestResponse;
import com.n11.userservice.dto.RestaurantDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Configuration
public class FeignClientConfig {

    @Value("${restaurant-service.url}")
    private String productServiceUrl;

    @Bean
    public RestaurantClient productClient() {
        return new RestaurantClient() {
            @Override
            public RestResponse<List<RestaurantDTO>> getNearbyRestaurants(Double latitude, Double longitude, Double distance) {
                // Use the dynamically retrieved base URL
                return null; // Implement the actual Feign client call
            }
        };
    }
}
