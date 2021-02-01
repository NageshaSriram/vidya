package com.vidya.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vidya.controller.UserController;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private String driverClassName;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Active profile: DEV");
		logger.info(username);
		return driverClassName;
	}
	
	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$Active profile: TEST");
		logger.info(username);
		return driverClassName;
	}
	
	@Profile("prod")
	@Bean
	public String prodDatabaseConnection() {
		logger.info("#############################################################Active profile: PROD");
		logger.info(username);
		return driverClassName;
	}
	

}
