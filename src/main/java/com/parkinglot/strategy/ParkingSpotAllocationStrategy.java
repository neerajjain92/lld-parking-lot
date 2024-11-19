package com.parkinglot.strategy;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.model.ParkingSpot;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface ParkingSpotAllocationStrategy {
    Optional<ParkingSpot> findNextAvailableSpot(VehicleType vehicleType);
}
