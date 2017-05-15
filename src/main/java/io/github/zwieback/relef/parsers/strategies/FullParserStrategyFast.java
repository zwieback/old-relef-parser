package io.github.zwieback.relef.parsers.strategies;

import io.github.zwieback.relef.entities.Catalog;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY_FAST;

@Service(FULL_STRATEGY_FAST)
public class FullParserStrategyFast extends AbstractFullParserStrategy {

    private static final Logger log = LogManager.getLogger(FullParserStrategyFast.class);

    private final CatalogParser catalogParser;
    private final ProductRepository productRepository;

    @Autowired
    public FullParserStrategyFast(CatalogsParser catalogsParser,
                                  BrandRepository brandRepository,
                                  CatalogRepository catalogRepository,
                                  CatalogLevelService catalogLevelService,
                                  CatalogParser catalogParser,
                                  ProductRepository productRepository) {
        super(catalogsParser, brandRepository, catalogRepository, catalogLevelService);
        this.catalogParser = catalogParser;
        this.productRepository = productRepository;
    }

    @Override
    void parseProductsOfCatalog(Catalog catalog) {
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
