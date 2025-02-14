package com.parkinglot.service.impl;

import com.parkinglot.dto.request.ParkingRequest;
import com.parkinglot.dto.request.UnParkingRequest;
import com.parkinglot.dto.response.ParkingRequestResponse;
import com.parkinglot.exceptions.InvalidParkingReceiptException;
import com.parkinglot.exceptions.NoAvailableSlotsException;
import com.parkinglot.exceptions.ParkingReceiptNotFoundException;
import com.parkinglot.model.ParkingReceipt;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.repository.ParkingReceiptRepository;
import com.parkinglot.repository.ParkingSpotRepository;
import com.parkinglot.service.ParkingLotService;
import com.parkinglot.strategy.ParkingSpotAllocationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingSpotAllocationStrategy parkingSpotAllocationStrategy;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingReceiptRepository parkingReceiptRepository;
    private final TransactionTemplate transactionTemplate;

    @Override
    public ParkingRequestResponse parkVehicle(ParkingRequest parkingRequest) {
        return transactionTemplate.execute(status -> {
            try {
                Optional<ParkingSpot> parkingSpotOptional = parkingSpotAllocationStrategy.findNextAvailableSpot(parkingRequest.getVehicleType());

                if (parkingSpotOptional.isEmpty()) {
                    throw new NoAvailableSlotsException(parkingRequest.getVehicleType());
                }
                final LocalDateTime now = LocalDateTime.now();
                final ParkingSpot parkingSpot = reserveParkingSpot(parkingRequest, parkingSpotOptional.get(), now);
                final ParkingReceipt parkingReceipt = generateParkingReceipt(parkingRequest, parkingSpot, now);
                return ParkingRequestResponse.builder()
                        .receiptNumber(parkingReceipt.getReceiptNumber())
                        .slotNumber(parkingReceipt.getSlotNumber())
                        .parkedTime(parkingReceipt.getEntryTime())
                        .licensePlateNumber(parkingReceipt.getLicensePlateNumber())
                        .build();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    @Override
    public void unParkVehicle(UnParkingRequest unParkingRequest) {
        ParkingReceipt parkingReceipt = parkingReceiptRepository.findByReceiptNumber(unParkingRequest.getParkingReceiptNumber());
        if (parkingReceipt == null) {
            throw new ParkingReceiptNotFoundException(unParkingRequest.getParkingReceiptNumber());
        }
        if (!parkingReceipt.isActive()) {
            throw new InvalidParkingReceiptException(unParkingRequest.getParkingReceiptNumber());
        }

        parkingReceipt.setExitTime(LocalDateTime.now());
        parkingReceiptRepository.save(parkingReceipt);
    }

    // Generate Parking Receipt
    private ParkingReceipt generateParkingReceipt(ParkingRequest parkingRequest, ParkingSpot parkingSpot, LocalDateTime currentTime) {
        ParkingReceipt parkingReceipt = ParkingReceipt.builder()
                .receiptNumber(UUID.randomUUID().toString())
                .slotNumber(parkingSpot.getSlotNumber())
                .floorNumber(parkingSpot.getFloorNumber())
                .wasEVSpot(parkingSpot.isHasEVCharger())
                .slotSize(parkingSpot.getSlotSize())
                .licensePlateNumber(parkingRequest.getLicensePlateNumber())
                .vehicleType(parkingRequest.getVehicleType())
                .entryTime(currentTime)
                .build();
        return parkingReceiptRepository.save(parkingReceipt);
    }

    private ParkingSpot reserveParkingSpot(ParkingRequest parkingRequest, final ParkingSpot parkingSpot, LocalDateTime currentTime) {
        // We found the parkingSpot, let's park the vehicle
        parkingSpot.setOccupied(true);
        parkingSpot.setLicensePlateNumber(parkingRequest.getLicensePlateNumber());
        parkingSpot.setOccupiedVehicleType(parkingRequest.getVehicleType());
        parkingSpot.setOccupiedAt(currentTime);
        parkingSpotRepository.save(parkingSpot);
        return parkingSpot;
    }
}
