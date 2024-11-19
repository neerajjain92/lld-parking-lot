package com.parkinglot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LldParkingLotApplication {

	public static void main(String[] args) {
		log.info("Starting LldParkingLotApplication");
		SpringApplication.run(LldParkingLotApplication.class, args);
	}

}
