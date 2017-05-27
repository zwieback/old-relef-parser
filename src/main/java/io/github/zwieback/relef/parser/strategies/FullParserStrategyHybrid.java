package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.CatalogParser;
import io.github.zwieback.relef.parsers.CatalogsParser;
import io.github.zwieback.relef.parsers.ProductParser;
import io.github.zwieback.relef.parsers.UrlParser;
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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_HYBRID;

@Service(FULL_STRATEGY_HYBRID)
public class FullParserStrategyHybrid extends AbstractFullParserStrategy {

    private static final Logger log = LogManager.getLogger(FullParserStrategyHybrid.class);

    private final CatalogParser catalogParser;
    private final ProductParser productParser;
    private final UrlParser urlParser;

    @Autowired
    public FullParserStrategyHybrid(CatalogsParser catalogsParser,
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
        List<Product> products = documents.stream()
                .map(document -> catalogParser.parseProducts(document, catalog.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info(String.format("Found %d products in catalog %d", products.size(), catalog.getId()));

        List<Product> existedProducts = findExistedProducts(collectProductIds(products));
        List<Product> parsedProducts = parseProductsWithoutDescription(products, existedProducts);
        processParsedProducts(parsedProducts);
    }

    private List<Product> parseProductsWithoutDescription(List<Product> parsedProducts, List<Product> existedProducts) {
        return existedProducts.stream()
                .map(product -> parseOrGetParsedProduct(parsedProducts, product))
                .collect(Collectors.toList());
    }

    private Product parseOrGetParsedProduct(List<Product> parsedProducts, @NotNull Product product) {
        if (product.getDescription() == null) {
            log.debug(String.format("Product with id %d has no description and will be parsed now", product.getId()));
            return parseProductByUrl(product.getUrl());
        }
        return findProductById(parsedProducts, product.getId());
    }

    private static Product findProductById(List<Product> products, @NotNull Long id) {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Product with id %d not found", id)));
    }

    @NotNull
    private Product parseProductByUrl(String productUrl) {
        Document productDocument = productParser.parseUrl(productUrl);
        Long catalogId = urlParser.parseCatalogIdFromProductUrl(productUrl);
        Long productId = urlParser.parseProductIdFromProductUrl(productUrl);
        return productParser.parseProduct(productDocument, catalogId, productId);
    }
}
