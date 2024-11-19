package com.parkinglot.service;

import com.parkinglot.dto.request.ParkingRequest;
import com.parkinglot.dto.response.ParkingRequestResponse;
import com.parkinglot.exceptions.SlotAllocationException;

public interface ParkingLotService {

    ParkingRequestResponse parkVehicle(ParkingRequest parkingRequest) throws SlotAllocationException;
    void unparkVehicle(ParkingRequest parkingRequest);
}
