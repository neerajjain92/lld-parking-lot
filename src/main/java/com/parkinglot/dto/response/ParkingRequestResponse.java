package com.parkinglot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParkingRequestResponse {
    // Unique number given to this parking receipt
    // which can be used to unpark Vehicle
    private final String receiptNumber;
    // ID of ParkingSlot where vehicle is parked
    private final String slotNumber;
    // Time at which vehicle was parked in the ParkingLot at this slot
    private final LocalDateTime parkedTime;
    // The Vehicle which is parked
    private final String licensePlateNumber;
}
