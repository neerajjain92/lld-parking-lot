package com.parkinglot.model;


import com.parkinglot.enums.SlotSize;
import com.parkinglot.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "parking_receipts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String receiptNumber;

    // We can't store Slot Reference since the occupancy on the slot changes overtime so we should just
    // store the information when this receipt was generated
    private String slotNumber;
    private Integer floorNumber;
    private boolean wasEVSpot;
    private SlotSize slotSize;

    // Vehicle Details
    private String licensePlateNumber;
    private VehicleType vehicleType;

    // Timing Details
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public boolean isActive() {
        return exitTime == null;
    }
}
