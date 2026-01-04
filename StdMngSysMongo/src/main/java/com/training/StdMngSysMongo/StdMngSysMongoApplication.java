package com.training.StdMngSysMongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableCaching
@SpringBootApplication
@EnableMongoRepositories
public class StdMngSysMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StdMngSysMongoApplication.class, args);
	}

}
