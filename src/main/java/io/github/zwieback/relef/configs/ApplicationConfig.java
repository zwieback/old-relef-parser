package io.github.zwieback.relef.configs;

import org.springframework.context.annotation.Import;

@Import({
        DatabaseConfig.class,
        DownloaderConfig.class,
        ExporterConfig.class,
        JacksonConfig.class,
        ParserConfig.class,
        ParserStrategyConfig.class,
        PropertyConfig.class,
        ServiceConfig.class,
        WebConfig.class
})
public class ApplicationConfig {
}
