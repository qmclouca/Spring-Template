package com.qmclouca.base;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.qmclouca.base.models",
		"com.qmclouca.base.repositories",
		"com.qmclouca.base.controllers",
		"com.qmclouca.base.services.Implementations",
		"com.qmclouca.base.utils.JwtGenerator.Implementations",
		"com.qmclouca.base.utils.JwtGenerator"
})
@EnableJpaRepositories
public class BaseApplication {
	@Bean
	public ModelMapper modelMapper() { return new ModelMapper(); }

	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
