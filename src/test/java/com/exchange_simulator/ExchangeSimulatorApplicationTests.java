package com.exchange_simulator;

import com.exchange_simulator.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangeSimulatorApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		System.out.println("Testing user service-----------------");

		userService.createSampleUser();

		System.out.println(userService.getUsers());
	}

}
