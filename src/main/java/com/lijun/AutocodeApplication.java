package com.lijun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching	//开启缓存注解
@MapperScan("com.lijun.**.dao.inft")
public class AutocodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutocodeApplication.class, args);
	}
}
