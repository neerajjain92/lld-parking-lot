package com.parkinglot.controller;

import com.parkinglot.dto.request.ParkingRequest;
import com.parkinglot.dto.request.UnParkingRequest;
import com.parkinglot.dto.response.ParkingRequestResponse;
import com.parkinglot.exceptions.InvalidParkingReceiptException;
import com.parkinglot.exceptions.NoAvailableSlotsException;
import com.parkinglot.exceptions.ParkingReceiptNotFoundException;
import com.parkinglot.exceptions.SlotAllocationException;
import com.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingLotService parkingLotService;

    @PostMapping("/park")
    public ResponseEntity<?> parkVehicle(@RequestBody ParkingRequest parkingRequest) {
        log.info("Received Vehicle Parking request for {}", parkingRequest.toString());
        ParkingRequestResponse parkingRequestResponse;
        try {
            parkingRequestResponse = parkingLotService.parkVehicle(parkingRequest);
        } catch (SlotAllocationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoAvailableSlotsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return new ResponseEntity<>(parkingRequestResponse, HttpStatus.OK);
    }

    @PostMapping("/unpark")
    public ResponseEntity<?> parkVehicle(@RequestBody UnParkingRequest unparkingRequest) {
        log.info("Received request to un-park vehicle parkingReceipt: {}", unparkingRequest.toString());
        try {
            parkingLotService.unParkVehicle(unparkingRequest);
        } catch (InvalidParkingReceiptException e) {
            return ResponseEntity.badRequest().build();
        } catch (ParkingReceiptNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
