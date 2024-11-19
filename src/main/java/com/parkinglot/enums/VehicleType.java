package com.parkinglot.enums;

public enum VehicleType {
    TWO_WHEELER(false),
    THREE_WHEELER(false),
    FOUR_WHEELER(false),
    TWO_WHEELER_EV(true),
    THREE_WHEELER_EV(true),
    FOUR_WHEELER_EV(true);

    private final boolean isElectric;

    VehicleType(boolean isElectric) {
        this.isElectric = isElectric;
    }

    public boolean needsEVCharger() {
        return isElectric;
    }
}
