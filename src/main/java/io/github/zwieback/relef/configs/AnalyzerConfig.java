package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.analyzers.MsProductAnalyzer;
import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.function.Function;

@Configuration
@ComponentScan("io.github.zwieback.relef.analyzers")
public class AnalyzerConfig {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final Function<String, MsProductImporter> msProductImporterFactory;

    @Autowired
    public AnalyzerConfig(JdbcTemplate jdbcTemplate,
                          ProductRepository productRepository,
                          Function<String, MsProductImporter> msProductImporterFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
        this.msProductImporterFactory = msProductImporterFactory;
    }

    @Bean
    public Function<String, MsProductAnalyzer> msProductAnalyzerFactory() {
        return this::msProductAnalyzer;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    MsProductAnalyzer msProductAnalyzer(String fileName) {
        MsProductImporter msProductImporter = msProductImporterFactory.apply(fileName);
        return new MsProductAnalyzer(jdbcTemplate, productRepository, msProductImporter);
    }
}
