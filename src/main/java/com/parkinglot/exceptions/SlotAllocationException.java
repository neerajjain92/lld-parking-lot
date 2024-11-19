package com.parkinglot.exceptions;

import com.parkinglot.enums.VehicleType;

public class SlotAllocationException extends ParkingLotException {
    public SlotAllocationException(VehicleType vehicleType) {
        super("Failed to allocate parking spot: "+ vehicleType);
    }
}
