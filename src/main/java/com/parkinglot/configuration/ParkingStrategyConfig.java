package com.parkinglot.configuration;

import com.parkinglot.repository.ParkingSpotRepository;
import com.parkinglot.strategy.EVPrioritySpotStrategy;
import com.parkinglot.strategy.FirstAvailableSpotStrategy;
import com.parkinglot.strategy.ParkingSpotAllocationStrategy;
import com.parkinglot.strategy.ProximityBasedSpotStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ParkingStrategyConfig {

    private final ParkingSpotRepository parkingSpotRepository;

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "firstAvailableSpot")
    public ParkingSpotAllocationStrategy firstAvailableSpotStrategy() {
        return new FirstAvailableSpotStrategy(parkingSpotRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "evPrioritySpot")
    public ParkingSpotAllocationStrategy evPrioritySpotStrategy() {
        return new EVPrioritySpotStrategy();
    }

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "proximitySpotStrategy")
    public ParkingSpotAllocationStrategy proximitySpotStrategy() {
        return new ProximityBasedSpotStrategy();
    }
}
