package com.oms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistributedOrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedOrderManagementApplication.class, args);
	}

}
