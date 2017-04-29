package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.parsers.InternalParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ParserConfigForTest extends ParserConfig {

    @Bean
    public InternalParser internalParser() {
        return mock(InternalParser.class);
    }
}
