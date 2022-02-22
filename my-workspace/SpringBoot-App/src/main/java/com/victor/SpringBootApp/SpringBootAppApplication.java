package com.victor.SpringBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Normally the main class will be in the root of the project to pick up the other classes, if not explicitly pass in the basePackage settings.
@SpringBootApplication(scanBasePackages = "com.victor")
@EnableJpaRepositories(basePackages = "com.victor")
@EntityScan(basePackages = {"com.victor"})
public class SpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
	}



}
