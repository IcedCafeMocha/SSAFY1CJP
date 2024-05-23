package com.cjp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@MapperScan("com.cjp.model.dao")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SsafycjpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsafycjpApplication.class, args);
	}

}
