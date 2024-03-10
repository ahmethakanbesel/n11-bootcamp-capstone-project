package com.n11.restaurantservice.general;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity implements BaseModel {
    private BaseAdditionalFields baseAdditionalFields;
}
