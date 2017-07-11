package io.github.zwieback.relef.utils.json;

import io.github.zwieback.relef.entities.dto.product.prices.ProductDto;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductPricesDeserializerTest extends AbstractDeserializerTest {

    @Test
    public void test_deserialize_should_return_one_product_price() {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/product_34259.json",
                ProductPricesDto.class);
        assertThat(productPrices.getAction()).isEqualTo("define");
        assertThat(productPrices.getCanBuy()).isTrue();
        assertThat(productPrices.getCompare()).isZero();
        assertThat(productPrices.getSoapm()).isFalse();
        assertThat(productPrices.getError()).isNull();
        assertThat(productPrices.getProductMap().size()).isEqualTo(1);

        Long key = productPrices.getProductMap().keySet().iterator().next();
        ProductDto product = productPrices.getProductMap().get(key);
        assertThat(key).isEqualTo(34259L);
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("2200.93"));
        assertThat(product.getAmount()).isEqualTo("0");
        assertThat(product.getAvailable()).isZero();
        assertThat(product.getOldPrice()).isEqualTo(BigDecimal.ZERO);
        assertThat(product.getBlackFriday()).isFalse();
    }

    @Test
    public void test_deserialize_should_return_no_product_price_with_error() {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/no_product.json",
                ProductPricesDto.class);
        assertThat(productPrices.getError()).isNotNull();
        assertThat(productPrices.getProductMap()).isEmpty();
    }

    @Test
    public void test_deserialize_should_return_many_product_prices() {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/catalogs.json",
                ProductPricesDto.class);
        assertThat(productPrices.getError()).isNull();
        assertThat(productPrices.getProductMap().size()).isEqualTo(49);
    }

    @Test
    public void test_deserialize_should_return_some_product_prices() {
        ProductPricesDto productPrices = readValue("classpath:json/product/prices/catalog_68526.json",
                ProductPricesDto.class);
        assertThat(productPrices.getError()).isNull();
        assertThat(productPrices.getProductMap().size()).isEqualTo(10);
    }
}
