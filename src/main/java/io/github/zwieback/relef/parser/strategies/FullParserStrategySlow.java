package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.*;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.CatalogLevelService;
import io.github.zwieback.relef.services.mergers.ProductMerger;
import io.github.zwieback.relef.services.mergers.ProductPriceMerger;
import io.github.zwieback.relef.web.services.ProductPriceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_SLOW;

@Service(FULL_STRATEGY_SLOW)
public class FullParserStrategySlow extends AbstractFullParserStrategy {

    private static final Logger log = LogManager.getLogger(FullParserStrategySlow.class);

    private final CatalogParser catalogParser;
    private final ProductParser productParser;
    private final UrlParser urlParser;

    @Autowired
    public FullParserStrategySlow(CatalogsParser catalogsParser,
                                  BrandRepository brandRepository,
                                  CatalogRepository catalogRepository,
                                  CatalogLevelService catalogLevelService,
                                  CatalogParser catalogParser,
                                  ProductParser productParser,
                                  UrlParser urlParser,
                                  ProductRepository productRepository,
                                  ProductPriceService productPriceService,
                                  ProductPriceMerger productPriceMerger,
                                  ProductMerger productMerger) {
        super(catalogsParser, brandRepository, catalogRepository, catalogLevelService, productRepository,
                productPriceService, productPriceMerger, productMerger);
        this.catalogParser = catalogParser;
        this.productParser = productParser;
        this.urlParser = urlParser;
    }

    @Override
    void parseProductsOfCatalog(Catalog catalog) {
        String url = catalog.getUrl();
        List<Document> documents = catalogParser.parseUrl(url);
        List<String> productUrls = documents.stream()
                .map(catalogParser::parseProductUrls)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info(String.format("Found %d products in catalog %d", productUrls.size(), catalog.getId()));
        parseProductsByUrls(productUrls);
    }

    private void parseProductsByUrls(List<String> productUrls) {
        int productSize = productUrls.size();
        AtomicInteger i = new AtomicInteger();
        List<Product> products = productUrls.stream()
                .map(url -> {
                    log.debug(String.format("Parse product %d of %d (%s)", i.incrementAndGet(), productSize, url));
                    return parseProductByUrl(url);
                })
                .collect(Collectors.toList());

        processParsedProducts(products);
    }

    @NotNull
    private Product parseProductByUrl(String productUrl) {
        Document productDocument = productParser.parseUrl(productUrl);
        Long catalogId = urlParser.parseCatalogIdFromProductUrl(productUrl);
        Long productId = urlParser.parseProductIdFromProductUrl(productUrl);
        return productParser.parseProduct(productDocument, catalogId, productId);
    }
}
