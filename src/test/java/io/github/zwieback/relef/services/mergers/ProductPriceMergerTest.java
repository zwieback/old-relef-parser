package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.configs.ServiceConfig;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class ProductPriceMergerTest {

    private static final Long PRODUCT_ID = 0L;
    private static final BigDecimal OLD_PRICE = BigDecimal.ZERO;
    private static final BigDecimal NEW_PRICE = BigDecimal.TEN;

    @SuppressWarnings("unused")
    @Autowired
    private ProductPriceMerger productPriceMerger;

    @Test
    public void test_mergePrices_should_merge_one_price() {
        List<Product> products = Collections.singletonList(new Product().setId(PRODUCT_ID).setPrice(OLD_PRICE));
        Map<Long, ProductDto> productMap = new HashMap<>();
        productMap.put(PRODUCT_ID, new ProductDto().setOldPrice(NEW_PRICE));
        ProductPricesDto productPricesDto = new ProductPricesDto().setProductMap(productMap);

        productPriceMerger.mergePrices(products, productPricesDto);
        assertNotNull(productPricesDto.getProductMap().get(PRODUCT_ID));
        assertEquals(products.get(0).getPrice(), productPricesDto.getProductMap().get(PRODUCT_ID).getPrice());
    }

    @Test
    public void test_mergePrices_should_not_merge_any_price() {
        List<Product> products = Collections.singletonList(new Product().setId(PRODUCT_ID).setPrice(OLD_PRICE));
        Map<Long, ProductDto> productMap = new HashMap<>();
        ProductPricesDto productPricesDto = new ProductPricesDto().setProductMap(productMap);

        productPriceMerger.mergePrices(products, productPricesDto);
        assertNull(productPricesDto.getProductMap().get(PRODUCT_ID));
        assertEquals(OLD_PRICE, products.get(0).getPrice());
    }
}
