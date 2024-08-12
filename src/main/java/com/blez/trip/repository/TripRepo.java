package com.blez.trip.repository;

import com.blez.trip.model.TripModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepo extends JpaRepository<TripModel,Integer> {
    @Query(value = "SELECT * FROM TripModel WHERE c_userId = :cUserId", nativeQuery = true)
    List<TripModel> findByCUserIdNative(@Param("cUserId") String cUserId);

    @Query(value = "SELECT * FROM TripModel WHERE r_userId = :rUserId", nativeQuery = true)
    List<TripModel> findByRUserIdNative(@Param("rUserId") String rUserId);



}
