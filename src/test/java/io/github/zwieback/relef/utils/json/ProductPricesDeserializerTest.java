package io.github.zwieback.relef.utils.json;

import io.github.zwieback.relef.entities.dto.product.prices.ProductDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductPricesDeserializerTest extends AbstractDeserializerTest {

    @Test
    public void test_deserialize_should_return_one_product_price() throws IOException {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/product_34259.json",
                ProductPricesDto.class);
        assertEquals("define", productPrices.getAction());
        assertEquals(true, productPrices.getCanBuy());
        assertEquals(0, productPrices.getCompare().intValue());
        assertEquals(false, productPrices.getSoapm());
        assertNull(productPrices.getError());
        assertEquals(1, productPrices.getProductMap().size());

        Long key = productPrices.getProductMap().keySet().iterator().next();
        ProductDto product = productPrices.getProductMap().get(key);
        assertEquals(34259L, key.longValue());
        assertEquals(new BigDecimal("2200.93"), product.getPrice());
        assertEquals("0", product.getAmount());
        assertEquals(0, product.getAvailable().intValue());
        assertEquals(new BigDecimal("0"), product.getOldPrice());
        assertEquals(false, product.getBlackFriday());
    }

    @Test
    public void test_deserialize_should_return_no_product_price_with_error() throws IOException {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/no_product.json",
                ProductPricesDto.class);
        assertNotNull(productPrices.getError());
        assertEquals(0, productPrices.getProductMap().size());
    }

    @Test
    public void test_deserialize_should_return_many_product_prices() throws IOException {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/catalogs.json",
                ProductPricesDto.class);
        assertNull(productPrices.getError());
        assertEquals(49, productPrices.getProductMap().size());
    }

    @Test
    public void test_deserialize_should_return_some_product_prices() throws IOException {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/catalog_68526.json",
                ProductPricesDto.class);
        assertNull(productPrices.getError());
        assertEquals(10, productPrices.getProductMap().size());
    }
}
