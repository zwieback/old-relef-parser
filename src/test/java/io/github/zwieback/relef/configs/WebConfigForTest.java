package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.web.rest.services.RestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class WebConfigForTest extends WebConfig {

    @Bean
    public RestService restService() {
        return mock(RestService.class);
    }
}
