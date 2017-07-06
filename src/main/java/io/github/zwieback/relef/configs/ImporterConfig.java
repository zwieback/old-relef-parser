package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("io.github.zwieback.relef.importers")
public class ImporterConfig {

    private final StringService stringService;

    @Autowired
    public ImporterConfig(StringService stringService) {
        this.stringService = stringService;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Lazy
    public MsProductImporter msProductImporter(String fileName) {
        return new MsProductImporter(stringService, fileName);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Lazy
    public SamsonProductImporter samsonProductImporter(String fileName) {
        return new SamsonProductImporter(stringService, fileName);
    }
}
