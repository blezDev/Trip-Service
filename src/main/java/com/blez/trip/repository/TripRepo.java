package com.blez.trip.repository;


import com.blez.trip.model.TripModel;
import com.blez.trip.model.CarpoolingRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepo extends JpaRepository<TripModel,Integer> {
    @Query(value = "SELECT * FROM trip_model WHERE c_user_id = :cUserId", nativeQuery = true)
    List<TripModel> findByCUserIdNative(@Param("cUserId") String cUserId);

    @Query(value = "SELECT * FROM trip_model WHERE r_user_id = :rUserId", nativeQuery = true)
    List<TripModel> findByRUserIdNative(@Param("rUserId") String rUserId);


    @Query(value = "SELECT * FROM carpooling_route",nativeQuery = true)
    List<Object> getAllCities();
}
