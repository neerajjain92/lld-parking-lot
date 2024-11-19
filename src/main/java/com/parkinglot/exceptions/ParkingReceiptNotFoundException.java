package com.parkinglot.exceptions;

public class ParkingReceiptNotFoundException extends ParkingLotException {
    public ParkingReceiptNotFoundException(String parkingReceiptNumber) {
        super(String.format("Parking receipt %s not found", parkingReceiptNumber));
    }
}
