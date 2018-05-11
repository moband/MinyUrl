package com.neueda.kgs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application {

	/**
	 * The name of the Cache for shorted URL Entity.
	 */
	public static final String CACHE_MINYLY = "minyly";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
