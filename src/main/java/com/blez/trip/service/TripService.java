package com.blez.trip.service;


import com.blez.trip.model.TripModel;
import com.blez.trip.model.CarpoolingRoute;
import com.blez.trip.utils.ResultState;

import java.util.List;

public interface TripService {

    ResultState<String> createTrip(TripModel tripModel);



    ResultState<String> deleteTrip(int id);

    ResultState<TripModel> getTripById(int id);

    ResultState<List<TripModel>> getTripsByCId(String cid);

    ResultState<List<TripModel>>  getTripByRid(String rId);

    ResultState<List<Object>> getAllCities();
}
