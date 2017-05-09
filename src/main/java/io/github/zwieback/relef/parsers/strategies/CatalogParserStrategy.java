package io.github.zwieback.relef.parsers.strategies;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.CatalogParser;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.CATALOG_STRATEGY;

@Service(CATALOG_STRATEGY)
public class CatalogParserStrategy extends ParserStrategy {

    private static final Logger log = LogManager.getLogger(CatalogParserStrategy.class);

    private final CatalogParser parser;
    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;

    private List<Long> catalogIds;

    @Autowired
    public CatalogParserStrategy(CatalogParser parser, CatalogRepository catalogRepository,
                                 ProductRepository productRepository) {
        this.parser = parser;
        this.catalogRepository = catalogRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void setEntityIds(String entityIds) {
        catalogIds = collectLongIds(entityIds);
    }

    @Override
    public void parse() {
        List<Long> existedIds = catalogRepository.findExistedIdsByIdIn(catalogIds);
        if (catalogIds.size() != existedIds.size()) {
            log.info("Only these catalog ids exist and will be parsed: " + existedIds);
        }
        List<Catalog> existedCatalogs = catalogRepository.findAll(existedIds);
        existedCatalogs.forEach(this::parseCatalog);
    }

    private void parseCatalog(Catalog existedCatalog) {
        String url = existedCatalog.getUrl();
        List<Document> documents = parser.parseUrl(url);
        List<Product> parsedProducts = documents.stream()
                .map(document -> parser.parseProducts(document, existedCatalog.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info(String.format("Found %d products in catalog %d", parsedProducts.size(), existedCatalog.getId()));
        saveProducts(parsedProducts);
    }

    @Transactional
    private void saveProducts(List<Product> products) {
        productRepository.save(products);
    }
}
