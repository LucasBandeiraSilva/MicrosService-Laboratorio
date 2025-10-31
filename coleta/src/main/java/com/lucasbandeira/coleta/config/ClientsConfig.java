package com.lucasbandeira.coleta.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.lucasbandeira.coleta.client")
public class ClientsConfig {
}
