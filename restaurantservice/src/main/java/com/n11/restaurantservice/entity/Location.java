package com.n11.restaurantservice.entity;

import org.springframework.data.solr.core.mapping.Indexed;

public class Location {

    @Indexed(name = "latitude", type = "double")
    private double latitude;

    @Indexed(name = "longitude", type = "double")
    private double longitude;
}
