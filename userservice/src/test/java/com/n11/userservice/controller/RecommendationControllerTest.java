package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userservice.UserServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UserServiceApplication.class)
class RecommendationControllerTest extends BaseControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = webAppContextSetup(context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldReturnAllRecommendationsWithLocation() throws Exception {
        double latitude = 40.7128;
        double longitude = -74.006;
        int distance = 10;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-location")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldReturnAllRecommendationsWithUser() throws Exception {
        long userId = 100L;
        double distance = 10.0;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-user")
                        .param("userId", String.valueOf(userId))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldReturnBadRequestErrorWhenDistanceNotInRange() throws Exception {
        double latitude = 40.7128;
        double longitude = -74.006;
        double distance = 15.0;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-location")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean failure = isFailure(mvcResult);
        assertTrue(failure);
    }

    @Test
    void shouldReturnBadRequestErrorWhenLatitudeNotInRange() throws Exception {
        double latitude = 95.0;
        double longitude = -74.006;
        double distance = 10.0;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-location")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean failure = isFailure(mvcResult);
        assertTrue(failure);
    }

    @Test
    void shouldReturnBadRequestErrorWhenLongitudeNotInRange() throws Exception {
        double latitude = 40.7128;
        double longitude = -190.0;
        double distance = 10.0;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-location")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean failure = isFailure(mvcResult);
        assertTrue(failure);
    }

    @Test
    void shouldReturnBadRequestErrorWhenUserIdIsInvalid() throws Exception {
        long userId = -1L;
        double distance = 10.0;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recommendations/with-user")
                        .param("userId", String.valueOf(userId))
                        .param("distance", String.valueOf(distance)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        boolean failure = isFailure(mvcResult);
        assertTrue(failure);
    }
}