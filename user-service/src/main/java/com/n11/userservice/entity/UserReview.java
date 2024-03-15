package com.n11.userservice.entity;

import com.n11.userservice.common.BaseEntity;
import com.n11.userservice.enums.ReviewScore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_reviews",
        indexes = {
                @Index(columnList = "user_id"),
                @Index(columnList = "restaurant_id"),
                @Index(columnList = "user_id, restaurant_id", unique = true)
        }
)
public class UserReview extends BaseEntity implements Serializable {
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

    // NOTE: This relationship couples the user and review entities. It is a good idea to avoid this kind of coupling.
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Column(name = "user_id", nullable = false)
    private Long userId;

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
