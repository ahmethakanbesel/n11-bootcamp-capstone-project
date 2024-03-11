package com.n11.userservice.service.entityservice;

import com.n11.userservice.entity.UserReview;
import com.n11.userservice.common.BaseEntityService;
import com.n11.userservice.repository.UserReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {
    private final UserReviewRepository repository;
    protected UserReviewEntityService(UserReviewRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<UserReview> findByRestaurantId(String restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }
}
