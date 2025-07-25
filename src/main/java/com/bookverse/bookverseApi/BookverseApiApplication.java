package com.bookverse.bookverseApi;

import com.bookverse.bookverseApi.modelMapper.BookMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class BookverseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookverseApiApplication.class, args);
	}

	@Bean
	public BookMapper bookMapper() {
		return new BookMapper();
	}

}
