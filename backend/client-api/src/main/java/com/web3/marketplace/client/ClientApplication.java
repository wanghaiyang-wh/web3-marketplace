package com.web3.marketplace.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.web3.marketplace.core.config.MyBatisPlusConfig;

@SpringBootApplication
@MapperScan("com.web3.marketplace.core.mapper")
@ComponentScan(basePackages = {"com.web3.marketplace.client", "com.web3.marketplace.core", "com.web3.marketplace.common"})
@Import(MyBatisPlusConfig.class)
@EnableScheduling
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
