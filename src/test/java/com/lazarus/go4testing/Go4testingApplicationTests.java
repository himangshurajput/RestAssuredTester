package com.lazarus.go4testing;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource("classpath:config/testing.properties")
@SpringBootTest
class Go4testingApplicationTests {

	@Test
	void contextLoads() {
	}

}
