package com.tcoveney.ordersrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.tcoveney.ordersrestapi.config.AppConfig;

@SpringBootApplication(exclude=HibernateJpaAutoConfiguration.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

}
