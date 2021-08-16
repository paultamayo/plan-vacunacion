package com.paultamayo.administrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServicioAdministradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAdministradorApplication.class, args);
	}

}
