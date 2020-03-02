package com.buulke.buulkestore;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class BuulkeStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuulkeStoreApplication.class, args);
	}

}
