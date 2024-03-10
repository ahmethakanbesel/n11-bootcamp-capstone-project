package com.n11.restaurantservice.repository;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {
    List<Restaurant> findByName(String name);

    @Query("type:?0 AND _query_:\"{!geofilt sfield=location pt=?1,?2 d=?3}\"")
    List<Restaurant> findNearbyByType(RestaurantType type, double latitude, double longitude, double distance);

    @Query("_query_:\"{!geofilt sfield=location pt=?0,?1 d=?2}\"")
    List<Restaurant> findNearby(double latitude, double longitude, double distance);
}
