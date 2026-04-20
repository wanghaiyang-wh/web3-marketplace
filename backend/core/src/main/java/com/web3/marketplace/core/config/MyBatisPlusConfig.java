package com.web3.marketplace.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.web3.marketplace.core", "com.web3.marketplace.common"})
@MapperScan("com.web3.marketplace.core.mapper")
public class MyBatisPlusConfig {
}
