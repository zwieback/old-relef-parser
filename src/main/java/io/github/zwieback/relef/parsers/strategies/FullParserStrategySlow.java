package io.github.zwieback.relef.parsers.strategies;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.*;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.CatalogLevelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY_SLOW;

@Service(FULL_STRATEGY_SLOW)
public class FullParserStrategySlow extends AbstractFullParserStrategy {

    private static final Logger log = LogManager.getLogger(FullParserStrategySlow.class);

    private CatalogParser catalogParser;
    private ProductParser productParser;
    private UrlParser urlParser;
    private ProductRepository productRepository;

    @Autowired
    public FullParserStrategySlow(CatalogsParser catalogsParser,
                                  BrandRepository brandRepository,
                                  CatalogRepository catalogRepository,
                                  CatalogLevelService catalogLevelService,
                                  CatalogParser catalogParser,
                                  ProductParser productParser,
                                  UrlParser urlParser,
                                  ProductRepository productRepository) {
        super(catalogsParser, brandRepository, catalogRepository, catalogLevelService);
        this.catalogParser = catalogParser;
        this.productParser = productParser;
        this.urlParser = urlParser;
        this.productRepository = productRepository;
    }

    @Override
    void parseProductsOfCatalog(Catalog catalog) {
        String url = catalog.getUrl();
        List<Document> documents = catalogParser.parseUrl(url);
        List<String> productUrls = documents.stream()
                .map(document -> catalogParser.parseProductUrls(document))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info(String.format("Found %d products in catalog %d", productUrls.size(), catalog.getId()));
        parseProductsByUrls(productUrls);
    }

    private void parseProductsByUrls(List<String> productUrls) {
        int productSize = productUrls.size();
        IntStream.range(0, productSize)
                .forEach(i -> {
                    log.debug(String.format("Parse product %d of %d (%s)", i + 1, productSize, productUrls.get(i)));
                    Product parsedProduct = parseProductByUrl(productUrls.get(i));
                    saveProduct(parsedProduct);
                });
    }

    @NotNull
    private Product parseProductByUrl(String productUrl) {
        Document productDocument = productParser.parseUrl(productUrl);
        Long catalogId = urlParser.parseCatalogIdFromProductUrl(productUrl);
        Long productId = urlParser.parseProductIdFromProductUrl(productUrl);
        return productParser.parseProduct(productDocument, catalogId, productId);
    }

    private void saveProduct(Product product) {
        productRepository.save(product);
    }
}
