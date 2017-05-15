package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import io.github.zwieback.relef.parsers.CatalogsParser;
import io.github.zwieback.relef.parser.strategies.exceptions.ExceededErrorsCountException;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.services.CatalogLevelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public abstract class AbstractFullParserStrategy extends ParserStrategy {

    private static final Logger log = LogManager.getLogger(AbstractFullParserStrategy.class);

    private final CatalogsParser catalogsParser;
    private final BrandRepository brandRepository;
    private final CatalogRepository catalogRepository;
    private final CatalogLevelService catalogLevelService;

    @Value("${site.domain.catalogs}")
    private String catalogsUrl;

    @Value("${max.errors.number:10}")
    private Integer maxErrorsNumber;

    @Autowired
    AbstractFullParserStrategy(CatalogsParser catalogsParser,
                               BrandRepository brandRepository,
                               CatalogRepository catalogRepository,
                               CatalogLevelService catalogLevelService) {
        this.catalogsParser = catalogsParser;
        this.brandRepository = brandRepository;
        this.catalogRepository = catalogRepository;
        this.catalogLevelService = catalogLevelService;
    }

    @Override
    public void setEntityIds(String entityIds) {
        throw new UnsupportedOperationException();
    }

    @Transactional(noRollbackFor = ExceededErrorsCountException.class)
    @Override
    public void parse() {
        Document catalogsDoc = catalogsParser.parseUrl(catalogsUrl);
        parseBrands(catalogsDoc);
        parseTreeOfCatalogs(catalogsDoc);
        parseCatalogsOfLastLevel(catalogsDoc);
    }

    private void parseBrands(Document catalogsDoc) {
        List<Brand> brands = catalogsParser.parseBrands(catalogsDoc);
        log.info(String.format("Found %d brands", brands.size()));
        saveBrands(brands);
    }

    private void saveBrands(List<Brand> brands) {
        brandRepository.save(brands);
    }

    private void parseTreeOfCatalogs(Document catalogsDoc) {
        Set<CatalogLevel> levels = catalogLevelService.collectActiveCatalogLevels();
        levels.forEach(level -> {
            List<String> catalogUrls = catalogsParser.parseCatalogUrls(catalogsDoc, level);
            log.info(String.format("Found %d catalogs of level %s", catalogUrls.size(), level));
        });
        List<Catalog> treeOfCatalogs = catalogsParser.parseTreeOfCatalogs(catalogsDoc, CatalogLevel.FIRST);
        saveTreeOfCatalogs(treeOfCatalogs);
    }

    private void saveTreeOfCatalogs(List<Catalog> catalogs) {
        catalogs.forEach(catalog -> {
            if (!catalog.getChildren().isEmpty()) {
                saveTreeOfCatalogs(catalog.getChildren());
            }
        });
        catalogRepository.save(catalogs);
    }

    private void parseCatalogsOfLastLevel(Document catalogsDoc) {
        CatalogLevel lastLevel = catalogLevelService.determineLastCatalogLevel();
        List<Catalog> catalogs = catalogsParser.parseCatalogsOfLevel(catalogsDoc, lastLevel);
        AtomicInteger errorCount = new AtomicInteger();
        IntStream.range(0, catalogs.size())
                .forEach(i -> {
                    Catalog catalog = catalogs.get(i);
                    log.info(String.format("Parse catalog %d (%d) of %d", i + 1, catalog.getId(), catalogs.size()));
                    try {
                        parseProductsOfCatalog(catalog);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        if (errorCount.incrementAndGet() > maxErrorsNumber) {
                            throw new ExceededErrorsCountException();
                        }
                    }
                });
    }

    abstract void parseProductsOfCatalog(Catalog catalog);
}
