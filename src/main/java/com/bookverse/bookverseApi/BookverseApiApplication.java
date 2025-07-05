package com.bookverse.bookverseApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookverseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookverseApiApplication.class, args);
	}

}
