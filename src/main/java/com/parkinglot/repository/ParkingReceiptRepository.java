package com.parkinglot.repository;

import com.parkinglot.model.ParkingReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingReceiptRepository extends JpaRepository<ParkingReceipt, Long> {
    ParkingReceipt findByReceiptNumber(String receiptNumber);
}
