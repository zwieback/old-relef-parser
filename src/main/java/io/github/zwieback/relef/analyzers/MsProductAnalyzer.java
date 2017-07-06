package io.github.zwieback.relef.analyzers;

import io.github.zwieback.relef.analyzers.dto.NameDistanceDto;
import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import io.github.zwieback.relef.importers.excel.MsProductImporter;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsProductAnalyzer extends Analyzer {

    private static final Logger log = LogManager.getLogger(MsProductAnalyzer.class);
    private static final double NAME_DISTANCE = 0.1;  // 10%, i.e. 90% of matching symbols
    private static final String NAME_DISTANCE_QUERY =
            "SELECT id, name, name <-> ? AS dist FROM t_product ORDER BY dist LIMIT 1";

    private final JdbcTemplate jdbcTemplate;
    private final ProductRepository productRepository;
    private final MsProductImporter msProductImporter;

    @Autowired
    public MsProductAnalyzer(JdbcTemplate jdbcTemplate,
                             ProductRepository productRepository,
                             MsProductImporter msProductImporter) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
        this.msProductImporter = msProductImporter;
    }

    @Override
    public void analyze() {
        List<MsProductDto> entities = msProductImporter.doImport();
        log.info(String.format("Number of product from MySklad = %d", entities.size()));
        analyzeMatchingByNames(entities);
        analyzeMatchingByArticles(entities);
    }

    private void analyzeMatchingByNames(List<MsProductDto> entities) {
        BeanPropertyRowMapper<NameDistanceDto> rowMapper = new BeanPropertyRowMapper<>(NameDistanceDto.class);
        long matchingNameCount = entities.stream()
                .filter(entity -> {
                    NameDistanceDto nameDistanceDto = jdbcTemplate.queryForObject(NAME_DISTANCE_QUERY,
                            new Object[]{entity.getName()}, rowMapper);
                    return nameDistanceDto != null && nameDistanceDto.getDist() <= NAME_DISTANCE;
                })
                .count();
        logMatchingCount("names", matchingNameCount, entities.size());
    }

    private void analyzeMatchingByArticles(List<MsProductDto> entities) {
        long matchingArticleCount = entities.stream()
                .filter(entity -> productRepository.countByArticle(entity.getArticle()) > 0)
                .count();
        logMatchingCount("articles", matchingArticleCount, entities.size());
    }

    private static void logMatchingCount(String fieldName, long matchingCount, int entityCount) {
        log.info(String.format("Number of matching %s = %d", fieldName, matchingCount));
        log.info(String.format("Percent of matching %s = %.2f%%", fieldName,
                (double) matchingCount / entityCount * 100));
    }
}
