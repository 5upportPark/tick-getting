package com.pjw.tickgettinig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TickgettinigApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickgettinigApplication.class, args);
	}

}
