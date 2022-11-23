package com.alipay.cloudrun;


import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Log4j2
public class AlipayCloudrunApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlipayCloudrunApplication.class, args);
    }
}
