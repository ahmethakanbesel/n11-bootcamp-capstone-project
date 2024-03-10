package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.general.BaseEntity;
import com.n11.restaurantservice.util.PointSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.geo.Point;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SolrDocument(collection = "restaurant")
@Getter
@Setter
public class Restaurant extends BaseEntity {
    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "name", type = "string")
    private String name;

    @Indexed(name = "type", type = "string")
    private RestaurantType type;

    @Indexed(name = "location", type = "location")
    @JsonSerialize(using = PointSerializer.class)
    private Point location;
}
