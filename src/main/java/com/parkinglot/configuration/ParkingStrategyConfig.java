package com.parkinglot.configuration;

import com.parkinglot.strategy.EVPrioritySpotStrategy;
import com.parkinglot.strategy.FirstAvailableSpotStrategy;
import com.parkinglot.strategy.ParkingSpotAllocationStrategy;
import com.parkinglot.strategy.ProximityBasedSpotStrategy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@Configurable
public class ParkingStrategyConfig {

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "firstAvailableSpot")
    private ParkingSpotAllocationStrategy firstAvailableSpotStrategy() {
        return new FirstAvailableSpotStrategy();
    }

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "evPrioritySpot")
    private ParkingSpotAllocationStrategy evPrioritySpotStrategy() {
        return new EVPrioritySpotStrategy();
    }

    @Bean
    @ConditionalOnProperty(name = "parking.allocation.strategy", havingValue = "proximitySpotStrategy")
    private ParkingSpotAllocationStrategy proximitySpotStrategy() {
        return new ProximityBasedSpotStrategy();
    }
}
