package com.desafioconexa.miguelm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MiguelmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiguelmApplication.class, args);
	}

}
