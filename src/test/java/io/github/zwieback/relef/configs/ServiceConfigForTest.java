package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ServiceConfigForTest extends ServiceConfig {

    @Bean
    public DateTimeService dateTimeService() {
        return mock(DateTimeService.class);
    }
}
