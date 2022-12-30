package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.StockServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockServiceClientConfiguration {

    @Bean
    public StockServiceClient stockServiceClient() {
        return new StockServiceClient();
    }
}
