package com.paultamayo.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServidorOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServidorOauthApplication.class, args);
	}

}
