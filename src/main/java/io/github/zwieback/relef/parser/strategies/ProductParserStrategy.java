package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.ProductParser;
import io.github.zwieback.relef.web.parsers.ProductPriceReceiver;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.mergers.ProductPriceMerger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.PRODUCT_STRATEGY;

@Service(PRODUCT_STRATEGY)
public class ProductParserStrategy extends ParserStrategy {

    private static final Logger log = LogManager.getLogger(ProductParserStrategy.class);

    private final ProductParser parser;
    private final ProductRepository repository;

    private List<Long> productIds;

    @Autowired
    public ProductParserStrategy(ProductParser parser,
                                 ProductRepository repository,
                                 ProductPriceReceiver priceParser,
                                 ProductPriceMerger priceMerger) {
        super(repository, priceParser, priceMerger);
        this.parser = parser;
        this.repository = repository;
    }

    @Override
    public void setEntityIds(String entityIds) {
        productIds = collectLongIds(entityIds);
    }

    @Transactional
    @Override
    public void parse() {
        List<Long> existedIds = repository.findExistedIdsByIdIn(productIds);
        if (productIds.size() != existedIds.size()) {
            log.info("Only these product ids exist and will be parsed: " + existedIds);
        }
        List<Product> existedProducts = repository.findAll(existedIds);
        List<Product> parsedProducts = existedProducts.stream().map(this::parseProduct).collect(Collectors.toList());
        getAndMergeProductPrices(parsedProducts);
        saveProducts(parsedProducts);
    }

    @NotNull
    private Product parseProduct(Product existedProduct) {
        String url = existedProduct.getUrl();
        Document document = parser.parseUrl(url);
        return parser.parseProduct(document, existedProduct.getCatalogId(), existedProduct.getId());
    }
}
