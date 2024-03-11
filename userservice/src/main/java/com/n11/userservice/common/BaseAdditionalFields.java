package com.n11.userservice.common;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
