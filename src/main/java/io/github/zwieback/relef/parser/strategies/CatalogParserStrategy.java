package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.CatalogParser;
import io.github.zwieback.relef.web.services.ProductPriceService;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.mergers.ProductPriceMerger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.CATALOG_STRATEGY;

@Service(CATALOG_STRATEGY)
public class CatalogParserStrategy extends ParserStrategy {

    private static final Logger log = LogManager.getLogger(CatalogParserStrategy.class);

    private final CatalogParser parser;
    private final CatalogRepository catalogRepository;

    private List<Long> catalogIds;

    @Autowired
    public CatalogParserStrategy(CatalogParser parser,
                                 CatalogRepository catalogRepository,
                                 ProductRepository productRepository,
                                 ProductPriceService productPriceService,
                                 ProductPriceMerger productPriceMerger) {
        super(productRepository, productPriceService, productPriceMerger);
        this.parser = parser;
        this.catalogRepository = catalogRepository;
    }

    @Override
    public void setEntityIds(String entityIds) {
        catalogIds = collectLongIds(entityIds);
    }

    @Transactional
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
        processParsedProducts(parsedProducts);
    }
}
