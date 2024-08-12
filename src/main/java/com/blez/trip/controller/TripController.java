package com.blez.trip.controller;

import com.blez.trip.model.ResponseBody;
import com.blez.trip.model.TripModel;
import com.blez.trip.service.TripService;
import com.blez.trip.utils.ResultState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {


    @Autowired
    private TripService tripService;


    @PostMapping("/createTrip")
    public ResponseEntity<ResponseBody> createTrip(@RequestBody TripModel tripModel) {
        ResultState<String> tripState = tripService.createTrip(tripModel);
        if (tripState instanceof ResultState.Success<String> success) {

            return ResponseEntity.ok(new ResponseBody(success.getData()));

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(tripState.getMessage()));
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseBody> deleteTrip(@PathVariable int id) {
        ResultState<String> tripState = tripService.deleteTrip(id);
        if (tripState instanceof ResultState.Success<String> success) {

            return ResponseEntity.ok(new ResponseBody(success.getData()));

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseBody(tripState.getMessage()));
        }
    }
    @PostMapping("/getTripById/{id}")
    public ResponseEntity<TripModel> getTripById(@PathVariable int id) {
        ResultState<TripModel> tripState = tripService.getTripById(id);
        if (tripState instanceof ResultState.Success<TripModel> success) {

            return ResponseEntity.ok(success.getData());

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tripState.getData());
        }
    }

    @PostMapping("/getTripsBycid/{id}")
    public ResponseEntity<List<TripModel>> getTripsByCId(@PathVariable String id) {
        ResultState<List<TripModel>> tripState = tripService.getTripsByCId(id);
        if (tripState instanceof ResultState.Success<List<TripModel>> success) {

            return ResponseEntity.ok(success.getData());

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tripState.getData());
        }
    }

    @PostMapping("/getTripByRid/{id}")
    public ResponseEntity<List<TripModel>> getTripByRid(@PathVariable String id) {
        ResultState<List<TripModel>> tripState = tripService.getTripByRid(id);
        if (tripState instanceof ResultState.Success<List<TripModel>> success) {

            return ResponseEntity.ok(success.getData());

        }else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tripState.getData());
        }
    }

}
