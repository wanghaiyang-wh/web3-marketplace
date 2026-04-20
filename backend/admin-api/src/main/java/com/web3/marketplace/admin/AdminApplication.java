package com.web3.marketplace.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import com.web3.marketplace.core.config.MyBatisPlusConfig;

@SpringBootApplication
@MapperScan("com.web3.marketplace.core.mapper")
@ComponentScan(basePackages = {"com.web3.marketplace.admin", "com.web3.marketplace.core", "com.web3.marketplace.common"})
@Import(MyBatisPlusConfig.class)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
