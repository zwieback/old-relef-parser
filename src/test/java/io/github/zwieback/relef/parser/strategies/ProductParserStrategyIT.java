package io.github.zwieback.relef.parser.strategies;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.repositories.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductParserStrategyIT extends AbstractParserStrategyIT {

    private static final long CATALOG_ID = 68711L;
    private static final long PRODUCT_ID = 34259L;

    private static final String INCORRECT_IDS = "incorrect ids";
    private static final String CORRECT_IDS = "34256,34259";

    @SuppressWarnings("unused")
    @Autowired
    private ProductParserStrategy productParserStrategy;

    @SuppressWarnings("unused")
    @Autowired
    private ProductRepository productRepository;

    @Override
    String getResourcePage() {
        return String.format("classpath:pages/catalog_%d_product_%d.html", CATALOG_ID, PRODUCT_ID);
    }

    @Override
    String getProductPricesFileName() {
        return "classpath:json/product/prices/product_34259.json";
    }

    @Test(expected = NumberFormatException.class)
    public void test_setEntityIds_should_throws_exception() {
        productParserStrategy.setEntityIds(INCORRECT_IDS);
    }

    @Test
    @DatabaseSetup("classpath:db/productData.xml")
    public void parse() {
        productParserStrategy.setEntityIds(CORRECT_IDS);
        productParserStrategy.parse();

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);

        Product product = productRepository.findOne(PRODUCT_ID);
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("2200.93"));
    }
}
