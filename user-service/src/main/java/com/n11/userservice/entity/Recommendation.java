package com.n11.userservice.entity;

import com.n11.userservice.dto.LocationDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Recommendation implements Serializable {
    String id;
    String name;
    String type;
    LocationDTO location;
    Float distance;
    Double userScore;
    Double weightedScore;
}