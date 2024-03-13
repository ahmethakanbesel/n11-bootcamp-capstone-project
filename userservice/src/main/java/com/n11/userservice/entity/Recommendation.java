package com.n11.userservice.entity;

import com.n11.userservice.dto.LocationDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recommendation {
    String id;
    String name;
    String type;
    LocationDTO location;
    Float distance;
    Double userScore;
    Double weightedScore;
}