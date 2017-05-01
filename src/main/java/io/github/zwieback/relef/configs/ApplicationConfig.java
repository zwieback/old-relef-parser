package io.github.zwieback.relef.configs;

import org.springframework.context.annotation.Import;

@Import({
        DatabaseConfig.class,
        ParserConfig.class,
        PropertyConfig.class,
        ServiceConfig.class
})
public class ApplicationConfig {
}