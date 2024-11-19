package com.parkinglot.strategy;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.model.ParkingSpot;

import java.util.Optional;

public class ProximityBasedSpotStrategy implements ParkingSpotAllocationStrategy {
    @Override
    public Optional<ParkingSpot> findNextAvailableSpot(VehicleType vehicleType) {
        return Optional.empty();
    }
}
