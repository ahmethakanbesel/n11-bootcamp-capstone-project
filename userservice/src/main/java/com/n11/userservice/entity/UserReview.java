package com.n11.userservice.entity;

import com.n11.userservice.enums.ReviewScore;
import com.n11.userservice.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_reviews")
public class UserReview extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "user_review_id_sequence",
            sequenceName = "user_review_id_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_review_id_sequence"
    )
    private Long id;

    /*
    Hence this case study does not mention a relation between an order and a review,
    I assume that a user can review a restaurant without making an order at most once.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "restaurant_id", nullable = false)
    private String restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "score", nullable = false)
    private ReviewScore score;

    @Column(name = "comment")
    private String comment;

    public double toNumericScore() {
        return switch (score) {
            case ONE -> 1.0;
            case TWO -> 2.0;
            case THREE -> 3.0;
            case FOUR -> 4.0;
            case FIVE -> 5.0;
        };
    }
}
