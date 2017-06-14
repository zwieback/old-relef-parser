package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.analyzers.MsProductAnalyzer;
import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("io.github.zwieback.relef.analyzers")
public class AnalyzerConfig {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final MsProductImporter msProductImporter;

    @Autowired
    public AnalyzerConfig(JdbcTemplate jdbcTemplate,
                          ProductRepository productRepository,
                          MsProductImporter msProductImporter) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
        this.msProductImporter = msProductImporter;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Lazy
    public MsProductAnalyzer msProductAnalyzer(String fileName) {
        return new MsProductAnalyzer(fileName, jdbcTemplate, productRepository, msProductImporter);
    }
}
