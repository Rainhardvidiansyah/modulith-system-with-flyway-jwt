package com.rainhard.modulith.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class RainhardModulithApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void shouldVerifyModuleStructure(){
		ApplicationModules modules = ApplicationModules.of(RainhardModulithApplication.class);
		modules.verify();
	}

}