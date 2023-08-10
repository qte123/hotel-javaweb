package com.tafleo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tafleo.hotel.mapper")
public class HotelBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBackApplication.class, args);
    }

}
