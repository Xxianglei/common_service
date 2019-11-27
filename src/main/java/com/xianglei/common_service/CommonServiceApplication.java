package com.xianglei.common_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.xianglei.temppark_service.mapper")
public class CommonServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CommonServiceApplication.class, args);
    }

}

