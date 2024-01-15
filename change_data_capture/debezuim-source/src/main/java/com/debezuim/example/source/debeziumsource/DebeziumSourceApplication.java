package com.debezuim.example.source.debeziumsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class DebeziumSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebeziumSourceApplication.class, args);
	}

}
