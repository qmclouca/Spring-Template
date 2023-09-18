package com.qmclouca.base;

import com.qmclouca.base.utils.annotations.DisableTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseApplicationTests {
	@DisableTest(reason = "Novos testes")
	@Test
	void contextLoads() {
	}

}
