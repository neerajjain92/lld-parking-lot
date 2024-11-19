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
@Table(name = "parking_spots")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotSize slotSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "occupied_vehicle_type")
    private VehicleType occupiedVehicleType;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    private boolean hasEVCharger;

    @Column(name = "is_occupied")
    private boolean occupied;

    @Version
    private Long version;

    private LocalDateTime occupiedAt;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "distance_to_exit")
    private Integer distanceToExit;

    public boolean canAccommodateVehicle(VehicleType vehicleType) {
        return !occupied // Should not be occupied
                && slotSize.canAccommodate(vehicleType) // should be big enough to accommodate vehicle
                && (!vehicleType.needsEVCharger() || hasEVCharger); // if EV Charger is required slot should have EVCharger.
    }
}
