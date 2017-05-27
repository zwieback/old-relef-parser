package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.mergers.ProductPriceMerger;
import io.github.zwieback.relef.web.services.ProductPriceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParserStrategy {

    private static final Logger log = LogManager.getLogger(ParserStrategy.class);
    private static final String DELIMITER = ",";

    static final String FULL_STRATEGY_FAST = "fullStrategyFast";
    static final String FULL_STRATEGY_SLOW = "fullStrategySlow";
    static final String CATALOG_STRATEGY = "catalogStrategy";
    static final String PRODUCT_STRATEGY = "productStrategy";

    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductPriceMerger productPriceMerger;

    ParserStrategy(ProductRepository productRepository,
                   ProductPriceService productPriceService,
                   ProductPriceMerger productPriceMerger) {
        this.productRepository = productRepository;
        this.productPriceService = productPriceService;
        this.productPriceMerger = productPriceMerger;
    }

    /**
     * Used for parsing only this entities.
     *
     * @param entityIds list of entity id (divided by delimiter)
     * @throws UnsupportedOperationException if the set operation is not supported by strategy
     * @throws NumberFormatException         if the string cannot be parsed as a {@code long}
     */
    abstract void setEntityIds(String entityIds);

    List<Long> collectLongIds(String entityIds) {
        return Arrays.stream(entityIds.split(DELIMITER)).map(Long::valueOf).collect(Collectors.toList());
    }

    public abstract void parse();

    /**
     * Process parsed products:
     * <ol>
     * <li>get product prices and apply it to products</li>
     * <li>save products to database</li>
     * </ol>
     *
     * @param products parsed products
     */
    void processParsedProducts(List<Product> products) {
        getAndMergeProductPrices(products);
        saveProducts(products);
    }

    private void getAndMergeProductPrices(List<Product> products) {
        ProductPricesDto productPricesDto = productPriceService.getPrices(products);
        log.info(String.format("Found %d prices for products", productPricesDto.getProductMap().size()));
        productPriceMerger.mergePrices(products, productPricesDto);
    }

    private void saveProducts(List<Product> products) {
        productRepository.save(products);
        productRepository.flush();
    }
}
