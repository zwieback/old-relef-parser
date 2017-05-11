package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.parsers.exceptions.HtmlParseException;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class CatalogEmptyProductIdParserTest extends AbstractCatalogParserTest {

    private static final List<Integer> PRODUCT_QUANTITIES = Collections.singletonList(1);

    @Override
    Long getCatalogId() {
        return 0L;
    }

    @Override
    int getPageCount() {
        return 1;
    }

    @Override
    List<Integer> getProductQuantities() {
        return PRODUCT_QUANTITIES;
    }

    @Override
    @Test(expected = HtmlParseException.class)
    public void test_parseProducts_should_returns_parsed_products() {
        super.test_parseProducts_should_returns_parsed_products();
    }
}
