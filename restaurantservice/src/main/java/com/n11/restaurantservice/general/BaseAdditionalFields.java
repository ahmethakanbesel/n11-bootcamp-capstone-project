package com.n11.restaurantservice.general;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAdditionalFields {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Long creatorId;
    private Long updaterId;
}
