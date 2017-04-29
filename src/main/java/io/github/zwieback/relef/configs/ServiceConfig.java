package io.github.zwieback.relef.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
@ComponentScan("io.github.zwieback.relef.services")
public class ServiceConfig {

    @Bean
    public Charset defaultCharset() {
        return StandardCharsets.UTF_8;
    }
}
