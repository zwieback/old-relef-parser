package io.github.zwieback.relef.web.services;

import io.github.zwieback.relef.configs.WebConfigForTest;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import io.github.zwieback.relef.utils.json.AbstractDeserializerTest;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        WebConfigForTest.class
})
public class ProductPriceServiceTest extends AbstractDeserializerTest {

    @SuppressWarnings("unused")
    @Autowired
    private ProductPriceService productPriceService;

    @SuppressWarnings("unused")
    @Autowired
    private RestService restService;

    @Test
    public void test_getPrices_should_return_prices_for_zero_products() throws IOException {
        setProductPricesDto(readValue("classpath:json/product/prices/no_product.json", ProductPricesDto.class));
        ProductPricesDto productPricesDto = productPriceService.getPrices(buildProductListStub(0));
        assertThat(productPricesDto.getProductMap()).isEmpty();
    }

    @Test
    public void test_getPrices_should_return_prices_for_one_product() throws IOException {
        setProductPricesDto(readValue("classpath:json/product/prices/product_34259.json", ProductPricesDto.class));
        ProductPricesDto productPricesDto = productPriceService.getPrices(buildProductListStub(1));
        assertThat(productPricesDto.getProductMap()).hasSize(1);
    }

    @Test
    public void test_getPrices_should_return_prices_for_ten_product() throws IOException {
        setProductPricesDto(readValue("classpath:json/product/prices/catalog_68526.json", ProductPricesDto.class));
        ProductPricesDto productPricesDto = productPriceService.getPrices(buildProductListStub(10));
        assertThat(productPricesDto.getProductMap()).hasSize(10);
    }

    private void setProductPricesDto(ProductPricesDto productPricesDto) {
        when(restService.post(anyString(), anyObject(), anyObject())).thenReturn(productPricesDto);
    }

    private static List<Product> buildProductListStub(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> new Product().setId(0L).setXmlId(UUID.randomUUID()).setDataType("basket"))
                .collect(Collectors.toList());
    }
}
