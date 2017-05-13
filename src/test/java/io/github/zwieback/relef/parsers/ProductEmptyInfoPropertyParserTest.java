package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;

public class ProductEmptyInfoPropertyParserTest extends AbstractProductParserTest {

    private static final Long CATALOG_ID = 0L;
    private static final Long PRODUCT_ID = 2L;

    @Override
    Long getProductId() {
        return PRODUCT_ID;
    }

    @Override
    Long getCatalogId() {
        return CATALOG_ID;
    }

    @Override
    Product getExpectedProduct() {
        return new Product()
                .setId(PRODUCT_ID)
                .setCatalogId(CATALOG_ID)
                .setPhotoUrl(null)
                .setPhotoCachedUrl(null)
                .setName(null)
                .setDescription(null)
                .setCode(null)
                .setArticle(null)
                .setBarcode(null)
                .setManufacturerCountry(null)
                .setParty("1 / 1 / 1")
                .setWeight(null)
                .setVolume(null)
                .setProperties(generatePropertiesStub(0))
                .setManufacturer(null)
                .setTradeMark(null);
    }
}
