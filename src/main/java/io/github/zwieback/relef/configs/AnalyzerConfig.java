package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.analyzers.MsProductAnalyzer;
import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("io.github.zwieback.relef.analyzers")
public class AnalyzerConfig {

    private final BeanFactory beanFactory;
    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;

    @Autowired
    public AnalyzerConfig(BeanFactory beanFactory,
                          JdbcTemplate jdbcTemplate,
                          ProductRepository productRepository) {
        this.beanFactory = beanFactory;
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Lazy
    public MsProductAnalyzer msProductAnalyzer(String fileName) {
        MsProductImporter msProductImporter = beanFactory.getBean(MsProductImporter.class, fileName);
        return new MsProductAnalyzer(jdbcTemplate, productRepository, msProductImporter);
    }
}
