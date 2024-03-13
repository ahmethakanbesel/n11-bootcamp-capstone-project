package com.n11.userservice.data;

import com.n11.userservice.entity.UserReview;
import com.n11.userservice.enums.ReviewScore;

import java.util.ArrayList;
import java.util.List;

public class UserReviewData {

    public static UserReview review() {
        UserReview review = new UserReview();
        review.setId(100L);
        review.setUserId(100L);
        review.setComment("comment");
        review.setScore(ReviewScore.FIVE);
        return review;
    }

    public static List<UserReview> reviews() {
        List<UserReview> reviews = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserReview review = new UserReview();
            review.setId((long) i);
            review.setUserId((long) i);
            review.setComment("comment" + i);
            review.setScore(ReviewScore.FIVE);
            reviews.add(review);
        }
        return reviews;
    }

}
