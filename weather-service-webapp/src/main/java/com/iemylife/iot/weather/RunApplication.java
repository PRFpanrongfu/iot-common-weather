package com.iemylife.iot.weather;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by prf on 2017/3/30.
 */
@SpringBootApplication
@MapperScan("com.iemylife.iot.weather.mapper")
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class);
    }
}
