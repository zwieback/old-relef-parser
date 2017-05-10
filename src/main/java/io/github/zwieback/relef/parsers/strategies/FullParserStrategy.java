package io.github.zwieback.relef.parsers.strategies;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.CatalogParser;
import io.github.zwieback.relef.parsers.CatalogsParser;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.CatalogLevelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY;

@Service(FULL_STRATEGY)
public class FullParserStrategy extends ParserStrategy {

    private static final Logger log = LogManager.getLogger(FullParserStrategy.class);

    private final CatalogsParser catalogsParser;
    private final CatalogParser catalogParser;
    private final BrandRepository brandRepository;
    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;
    private final CatalogLevelService catalogLevelService;

    @Value("${site.domain.catalogs}")
    private String catalogsUrl;

    @Value("${max.errors.number:10}")
    private Integer maxErrorsNumber;

    public FullParserStrategy(CatalogsParser catalogsParser, CatalogParser catalogParser,
                              BrandRepository brandRepository, CatalogRepository catalogRepository,
                              ProductRepository productRepository, CatalogLevelService catalogLevelService) {
        this.catalogsParser = catalogsParser;
        this.catalogParser = catalogParser;
        this.brandRepository = brandRepository;
        this.catalogRepository = catalogRepository;
        this.productRepository = productRepository;
        this.catalogLevelService = catalogLevelService;
    }

    @Override
    public void setEntityIds(String entityIds) {
        throw new UnsupportedOperationException();
    }

    @Transactional
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
                    log.info(String.format("Parse catalog %d (%d) of %d", i, catalogs.get(i).getId(), catalogs.size()));
                    try {
                        parseProductsOfCatalog(catalogs.get(i));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        if (errorCount.incrementAndGet() > maxErrorsNumber) {
                            throw new RuntimeException("Exceeded the threshold number of errors");
                        }
                    }
                });
    }

    private void parseProductsOfCatalog(Catalog catalog) {
        String url = catalog.getUrl();
        List<Document> documents = catalogParser.parseUrl(url);
        List<Product> products = documents.stream()
                .map(document -> catalogParser.parseProducts(document, catalog.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info(String.format("Found %d products in catalog %d", products.size(), catalog.getId()));
        saveProducts(products);
    }

    private void saveProducts(List<Product> products) {
        productRepository.save(products);
    }
}
