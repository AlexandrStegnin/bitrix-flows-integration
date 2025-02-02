package com.ddkolesnik.bitrixflowsintegration;

import com.ddkolesnik.bitrixflowsintegration.model.ContactFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:private.properties")
public class BitrixFlowsIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitrixFlowsIntegrationApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ContactFilter getContactFilter() {
        return new ContactFilter();
    }

}
