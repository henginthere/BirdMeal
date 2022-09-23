package com.backend.birdmeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaAuditing
public class BirdmealApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:secret.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(BirdmealApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
		//SpringApplication.run(BirdmealApplication.class, args);
	}

}
