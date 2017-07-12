package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Manufacturer;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.ProductProperty;
import io.github.zwieback.relef.entities.TradeMark;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractProductParserTest extends AbstractParserTest {

    @SuppressWarnings("unused")
    @Autowired
    private ProductParser productParser;

    abstract Long getProductId();

    abstract Long getCatalogId();

    @Override
    String getResourcePage() {
        return String.format("classpath:pages/catalog_%d_product_%d.html", getCatalogId(), getProductId());
    }

    abstract Product getExpectedProduct();

    @Test
    public void test_parseProduct_should_returns_parsed_product() {
        Document productDocument = productParser.parseUrl("");
        Product product = productParser.parseProduct(productDocument, getCatalogId(), getProductId());

        compareProducts(getExpectedProduct(), product);
    }

    private static void compareProducts(Product expectedProduct, Product actualProduct) {
        String[] fieldsToIgnore = {"url", "lastUpdate", "properties"};
        assertThat(actualProduct).isEqualToIgnoringGivenFields(expectedProduct, fieldsToIgnore);
        assertThat(actualProduct.getProperties()).hasSameSizeAs(expectedProduct.getProperties());
    }

    static List<ProductProperty> generatePropertiesStub(int size) {
        return IntStream.range(0, size).mapToObj(i -> (ProductProperty) null).collect(Collectors.toList());
    }

    @NotNull
    static Manufacturer generateManufacturer(@NotNull String name, @NotNull String url) {
        return new Manufacturer(name, url);
    }

    @NotNull
    static TradeMark generateTradeMark(@NotNull String name, @NotNull String url) {
        return new TradeMark(name, url);
    }
}
