package com.n11.userservice.entity;

import com.n11.userservice.common.BaseEntity;
import com.n11.userservice.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(columnList = "username"),
        }
)
public class User extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Long id;

    @Column(name = "name", length = 63, nullable = false)
    private String name;

    @Column(name = "surname", length = 63, nullable = false)
    private String surname;

    @Column(name = "username", length = 15, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 63, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 15, unique = true)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "latitude", nullable = false)
    @Range(min=-90, max=90)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    @Range(min=-180, max=180)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15, nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
}
