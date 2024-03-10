package com.n11.restaurantservice.repository;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.enums.RestaurantType;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {
    List<Restaurant> findByName(String name);

    //@Query("type:?0 AND _query_:\"{!geofilt sfield=location pt=?1,?2 d=?3}\"")
    //List<Restaurant> findNearbyByType(RestaurantType type, double latitude, double longitude, double distance);

    //@Query("_query_:\"{!geofilt sfield=location pt=?0,?1 d=?2}\"")
    //List<Restaurant> findNearby(double latitude, double longitude, double distance);

    // Why not Within (circular) instead Near ?
    // Most cities planned in a grid pattern, so it is more efficient to use a square or rectangular area.
    //List<Restaurant> findByLocationNear(Point location, Distance distance);
    // q={!bbox pt=77.29,58.75 sfield=location d=5 score=kilometers sort=score asc geo=false}
    //@Query(value = "{!bbox pt=?0 sfield=location d=?1 score=kilometers geo=false}", filters = "*,score")

    @Query(value = "{!bbox pt=?0 sfield=location d=?1 score=kilometers geo=false}")
    List<Restaurant> findNearby(Point location, Distance distance);

    @Query(value = "type:?0 AND {!bbox pt=?1 sfield=location d=?2 score=kilometers geo=false}")
    List<Restaurant> findNearbyByType(RestaurantType type, Point location, Distance distance);
}
