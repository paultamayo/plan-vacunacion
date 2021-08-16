package com.paultamayo.ciudadano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServicioCiudadanoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCiudadanoApplication.class, args);
	}

}
