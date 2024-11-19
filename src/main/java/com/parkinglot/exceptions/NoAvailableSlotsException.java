package com.parkinglot.exceptions;

import com.parkinglot.enums.VehicleType;

public class NoAvailableSlotsException extends ParkingLotException {
    public NoAvailableSlotsException(VehicleType vehicleType) {
        super("No parking slots available for VehicleType: "+ vehicleType);
    }
}
