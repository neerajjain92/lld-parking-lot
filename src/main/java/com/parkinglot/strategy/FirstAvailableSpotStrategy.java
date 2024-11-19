package com.parkinglot.strategy;

import com.parkinglot.enums.VehicleType;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.repository.ParkingSpotRepository;

import java.util.List;
import java.util.Optional;

public class FirstAvailableSpotStrategy implements ParkingSpotAllocationStrategy{

    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public Optional<ParkingSpot> findNextAvailableSpot(VehicleType vehicleType) {
        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByOccupiedFalse();
        return parkingSpots.stream().filter(parkingSpot -> parkingSpot.canAccommodateVehicle(vehicleType)).findFirst();
    }
}
