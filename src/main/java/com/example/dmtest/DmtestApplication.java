package com.example.dmtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan(basePackages = "com.example.dmtest.mapper")
public class DmtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmtestApplication.class, args);
    }

}
