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

import static org.junit.Assert.assertEquals;

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
        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getCatalogId(), actualProduct.getCatalogId());
        assertEquals(expectedProduct.getPhotoUrl(), actualProduct.getPhotoUrl());
        assertEquals(expectedProduct.getPhotoCachedUrl(), actualProduct.getPhotoCachedUrl());
        assertEquals(expectedProduct.getName(), actualProduct.getName());
        assertEquals(expectedProduct.getDescription(), actualProduct.getDescription());
        assertEquals(expectedProduct.getCode(), actualProduct.getCode());
        assertEquals(expectedProduct.getArticle(), actualProduct.getArticle());
        assertEquals(expectedProduct.getBarcode(), actualProduct.getBarcode());
        assertEquals(expectedProduct.getManufacturerCountry(), actualProduct.getManufacturerCountry());
        assertEquals(expectedProduct.getParty(), actualProduct.getParty());
        assertEquals(expectedProduct.getWeight(), actualProduct.getWeight());
        assertEquals(expectedProduct.getVolume(), actualProduct.getVolume());
        assertEquals(expectedProduct.getXmlId(), actualProduct.getXmlId());
        assertEquals(expectedProduct.getDataType(), actualProduct.getDataType());
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        assertEquals(expectedProduct.getAmount(), actualProduct.getAmount());
        assertEquals(expectedProduct.getAvailable(), actualProduct.getAvailable());
        assertEquals(expectedProduct.getOldPrice(), actualProduct.getOldPrice());
        assertEquals(expectedProduct.getBlackFriday(), actualProduct.getBlackFriday());
        assertEquals(expectedProduct.getProperties().size(), actualProduct.getProperties().size());
        assertEquals(expectedProduct.getManufacturer(), actualProduct.getManufacturer());
        assertEquals(expectedProduct.getTradeMark(), actualProduct.getTradeMark());
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
