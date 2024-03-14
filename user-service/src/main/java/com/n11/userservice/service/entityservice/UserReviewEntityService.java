package com.n11.userservice.service.entityservice;

import com.n11.userservice.common.BaseEntityService;
import com.n11.userservice.common.GeneralErrorMessage;
import com.n11.userservice.entity.User;
import com.n11.userservice.entity.UserReview;
import com.n11.userservice.exceptions.BadRequestException;
import com.n11.userservice.repository.UserReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserReviewEntityService extends BaseEntityService<UserReview, UserReviewRepository> {
    private final UserReviewRepository repository;
    private final UserEntityService userEntityService;

    public UserReviewEntityService(UserReviewRepository repository, UserEntityService userEntityService) {
        super(repository);
        this.repository = repository;
        this.userEntityService = userEntityService;
    }

    public List<UserReview> findByRestaurantId(String restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    public UserReview saveWithUserControl(UserReview userReview) {
        Optional<User> user = userEntityService.findById(userReview.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException(GeneralErrorMessage.USER_NOT_FO2UND);
        }
        return repository.save(userReview);
    }
}
