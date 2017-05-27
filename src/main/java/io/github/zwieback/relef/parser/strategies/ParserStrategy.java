package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.mergers.ProductMerger;
import io.github.zwieback.relef.services.mergers.ProductPriceMerger;
import io.github.zwieback.relef.web.services.ProductPriceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ParserStrategy {

    private static final Logger log = LogManager.getLogger(ParserStrategy.class);
    private static final String DELIMITER = ",";

    static final String FULL_STRATEGY_FAST = "fullStrategyFast";
    static final String FULL_STRATEGY_SLOW = "fullStrategySlow";
    static final String FULL_STRATEGY_HYBRID = "fullStrategyHybrid";
    static final String CATALOG_STRATEGY = "catalogStrategy";
    static final String PRODUCT_STRATEGY = "productStrategy";

    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductPriceMerger productPriceMerger;
    private final ProductMerger productMerger;

    ParserStrategy(ProductRepository productRepository,
                   ProductPriceService productPriceService,
                   ProductPriceMerger productPriceMerger,
                   ProductMerger productMerger) {
        this.productRepository = productRepository;
        this.productPriceService = productPriceService;
        this.productPriceMerger = productPriceMerger;
        this.productMerger = productMerger;
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
     * <li>merge existed and parsed products</li>
     * <li>save merged products to database</li>
     * </ol>
     *
     * @param parsedProducts parsed products
     */
    void processParsedProducts(List<Product> parsedProducts) {
        getAndMergeProductPrices(parsedProducts);
        Set<Long> productIds = collectProductIds(parsedProducts);
        List<Product> existedProducts = findExistedProducts(productIds);
        List<Product> mergedProducts = mergeProducts(existedProducts, parsedProducts);
        saveProducts(mergedProducts);
    }

    private void getAndMergeProductPrices(List<Product> products) {
        ProductPricesDto productPricesDto = productPriceService.getPrices(products);
        log.info(String.format("Found %d prices for products", productPricesDto.getProductMap().size()));
        productPriceMerger.mergePrices(products, productPricesDto);
    }

    Set<Long> collectProductIds(List<Product> products) {
        return products.stream().map(Product::getId).collect(Collectors.toSet());
    }

    List<Product> findExistedProducts(Set<Long> productIds) {
        return productRepository.findAll(productIds);
    }

    private List<Product> mergeProducts(List<Product> existedProducts, List<Product> parsedProducts) {
        return productMerger.merge(existedProducts, parsedProducts);
    }

    private void saveProducts(List<Product> products) {
        productRepository.save(products);
        productRepository.flush();
    }
}
