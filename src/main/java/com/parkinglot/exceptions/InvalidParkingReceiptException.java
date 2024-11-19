package com.parkinglot.exceptions;

public class InvalidParkingReceiptException extends ParkingLotException {
    public InvalidParkingReceiptException(String receiptNumber) {
        super(String.format("Invalid or already used parking receipt: %s", receiptNumber));
    }
}