package com.n11.restaurantservice.repository;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {
    @Query("name:*?0*")
    List<Restaurant> findByNameContaining(String name);

    @Query(value = "{!bbox pt=?0 sfield=location d=?1 score=kilometers geo=false}")
    List<Restaurant> findNearby(Point location, Distance distance, Sort sort);

    @Query(value = "type:?0 AND {!bbox pt=?1 sfield=location d=?2 score=kilometers geo=false}")
    List<Restaurant> findNearbyByType(RestaurantType type, Point location, Distance distance);
}
