package com.uniminuto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EntityScan(basePackages = {"com.uniminuto.model"})
@EnableJpaRepositories(basePackages = {"com.uniminuto.dao"})
@ComponentScan(basePackages = {"com.uniminuto.sevice","com.uniminuto.controller","com.uniminuto.security","com.uniminuto"})
public class EstiloAlfaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstiloAlfaApplication.class, args);
	}

}
