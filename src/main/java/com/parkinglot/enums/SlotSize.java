package com.parkinglot.enums;

import java.util.List;

import static com.parkinglot.enums.VehicleType.*;

public enum SlotSize {

    XXL(List.of(FOUR_WHEELER, THREE_WHEELER, TWO_WHEELER, FOUR_WHEELER_EV, THREE_WHEELER_EV, TWO_WHEELER_EV)),
    XL(List.of(THREE_WHEELER, TWO_WHEELER,  THREE_WHEELER_EV, TWO_WHEELER_EV)),
    L(List.of(TWO_WHEELER, TWO_WHEELER_EV));

    private final List<VehicleType> allowedVehicleType;
    SlotSize(List<VehicleType> allowedVehicleType) {
        this.allowedVehicleType = allowedVehicleType;
    }

    public boolean canAccommodate(VehicleType vehicleType) {
        return allowedVehicleType.contains(vehicleType);
    }
}
