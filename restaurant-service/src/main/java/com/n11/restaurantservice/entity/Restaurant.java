package com.n11.restaurantservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n11.restaurantservice.common.BaseEntity;
import com.n11.restaurantservice.enums.RestaurantType;
import com.n11.restaurantservice.util.PointSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.Score;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SolrDocument(collection = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity {
    @Id
    @Field
    @Indexed(name = "id", type = "string")
    private String id;

    @Field
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 63, message = "Name must be between 3 and 63 characters")
    @Indexed(name = "name", type = "string")
    private String name;

    @Field
    @NotBlank(message = "Type cannot be blank")
    @Indexed(name = "type", type = "string")
    private RestaurantType type;

    @Field
    @NotNull(message = "Location cannot be null")
    @Indexed(type = "location")
    @JsonSerialize(using = PointSerializer.class)
    private Point location;

    @Score
    private Float score;
}
