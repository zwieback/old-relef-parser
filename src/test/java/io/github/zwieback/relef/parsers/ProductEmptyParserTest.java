package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.exceptions.HtmlParseException;
import org.junit.Test;

public class ProductEmptyParserTest extends AbstractProductParserTest {

    private static final Long CATALOG_ID = 0L;
    private static final Long PRODUCT_ID = 0L;

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
        return null;
    }

    @Override
    @Test(expected = HtmlParseException.class)
    public void test_parseProduct_should_returns_parsed_product() {
        super.test_parseProduct_should_returns_parsed_product();
    }
}
