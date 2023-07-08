package com.qmclouca.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.qmclouca.base.models",
		"com.qmclouca.base.repositories",
		"com.qmclouca.base.controllers"
})
public class BaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
