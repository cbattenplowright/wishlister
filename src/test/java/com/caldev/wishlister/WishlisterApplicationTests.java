package com.caldev.wishlister;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.annotation.ApplicationScope;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WishlisterApplication.class)
class WishlisterApplicationTests {

	@Test
	void contextLoads() {
	}

}
