package com.parkinglot.dto.request;

import com.parkinglot.enums.VehicleType;
import lombok.Data;

@Data
public class ParkingRequest {
    private String licensePlateNumber;
    private VehicleType vehicleType;
}
