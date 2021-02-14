package com.faramarz.spring.Covid19RestApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/*@EnableScheduling
@EnableTransactionManagement*/
@EnableTransactionManagement
@SpringBootApplication
public class Covid19RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19RestApiApplication.class, args);
	}

}
