package com.lijun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lijun.**.dao.inft")
public class AutocodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutocodeApplication.class, args);
	}
}
