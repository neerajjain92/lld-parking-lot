package com.parkinglot.controller;

import com.parkinglot.dto.request.ParkingRequest;
import com.parkinglot.dto.response.ParkingRequestResponse;
import com.parkinglot.exceptions.SlotAllocationException;
import com.parkinglot.service.ParkingLotService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/parking")
@Slf4j
public class ParkingController {

    private ParkingLotService parkingLotService;

    @PostMapping("/park")
    public ResponseEntity<ParkingRequestResponse> parkVehicle(@RequestBody ParkingRequest parkingRequest) {
        log.info(String.format("Received Vehicle Parking request for %s", parkingRequest.toString()));
        ParkingRequestResponse parkingRequestResponse;
        try {
            parkingRequestResponse = parkingLotService.parkVehicle(parkingRequest);
        } catch (SlotAllocationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(parkingRequestResponse, HttpStatus.OK);
    }
}
