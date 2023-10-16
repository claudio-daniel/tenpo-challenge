package cl.tenpo.rest.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestClientConfiguration {
    @Value("${spring.application.read.time.out}")
    private Integer readTimeOut;

    @Value("${spring.application.connection.time.out}")
    private Integer connectionTimeOut;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(readTimeOut))
                .setConnectTimeout(Duration.ofSeconds(connectionTimeOut))
                .build();
    }
}