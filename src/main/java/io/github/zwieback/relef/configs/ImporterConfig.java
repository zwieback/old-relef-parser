package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.importers.excel.ExcelCellReader;
import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
@ComponentScan("io.github.zwieback.relef.importers")
public class ImporterConfig {

    private final ExcelCellReader excelCellReader;

    @Autowired
    public ImporterConfig(ExcelCellReader excelCellReader) {
        this.excelCellReader = excelCellReader;
    }

    @Bean
    public Function<String, MsProductImporter> msProductImporterFactory() {
        return this::msProductImporter;
    }

    @Bean
    public Function<String, SamsonProductImporter> samsonProductImporterFactory() {
        return this::samsonProductImporter;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    MsProductImporter msProductImporter(String fileName) {
        return new MsProductImporter(excelCellReader, fileName);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SamsonProductImporter samsonProductImporter(String fileName) {
        return new SamsonProductImporter(excelCellReader, fileName);
    }
}
