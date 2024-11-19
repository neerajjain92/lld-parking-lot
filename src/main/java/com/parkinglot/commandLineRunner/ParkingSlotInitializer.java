package com.parkinglot.commandLineRunner;

import com.parkinglot.enums.SlotSize;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParkingSlotInitializer implements CommandLineRunner {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public void run(String... args) throws Exception {
        if (parkingSpotRepository.count() > 0) {
            return; // Don't initialize, if already available
        }

        final List<ParkingSpot> parkingSpotList = new ArrayList<>();

        // Ground Floor : XXL Slots (1 - 10)
        for (int i=1; i<= 10;i++) {
            parkingSpotList.add(ParkingSpot.builder()
                    .slotNumber(String.format("G-XXL-%02d", i))
                    .slotSize(SlotSize.XXL)
                    .occupied(true)
                    .floorNumber(0)
                    .distanceToExit(i) // Closer spots have lower numbers
                    .hasEVCharger(i <= 3)
                    .version(0L)
                    .build());
        }
        // First Floor: XL Slots (1 - 10)
        for (int i=1; i<= 10;i++) {
            parkingSpotList.add(ParkingSpot.builder()
                    .slotNumber(String.format("F1-XL-%02d", i))
                    .slotSize(SlotSize.XL)
                    .occupied(false)
                    .floorNumber(1)
                    .distanceToExit(i+10) // Closer spots have lower numbers
                    .hasEVCharger(i <= 2)
                    .version(0L)
                    .build());
        }

        // Second Floor: L Slots (1- 20)
        for (int i=1; i<= 10;i++) {
            parkingSpotList.add(ParkingSpot.builder()
                    .slotNumber(String.format("F2-L-%02d", i))
                    .slotSize(SlotSize.L)
                    .occupied(false)
                    .floorNumber(2)
                    .distanceToExit(i+20) // Closer spots have lower numbers
                    .hasEVCharger(i <= 4)
                    .version(0L)
                    .build());
        }

        List<ParkingSpot> savedSpots = parkingSpotRepository.saveAll(parkingSpotList);
        log.info(String.format("Parking slots initialized and total slots are %02d", savedSpots.size()));
    }
}
