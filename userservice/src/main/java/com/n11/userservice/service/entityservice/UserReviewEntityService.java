package com.n11.userservice.service.entityservice;

import com.n11.userservice.entity.UserReview;
import com.n11.userservice.common.BaseEntityService;
import com.n11.userservice.repository.UserReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {
    protected UserReviewEntityService(UserReviewRepository repository) {
        super(repository);
    }
}
