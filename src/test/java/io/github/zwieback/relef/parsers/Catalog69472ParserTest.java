package io.github.zwieback.relef.parsers;

import java.util.Arrays;
import java.util.List;

public class Catalog69472ParserTest extends AbstractCatalogParserTest {

    private static final List<Integer> PRODUCT_QUANTITIES = Arrays.asList(48, 48, 48, 48, 48, 48, 48, 48, 20);

    @Override
    Long getCatalogId() {
        return 69472L;
    }

    @Override
    int getPageCount() {
        return 9;
    }

    @Override
    List<Integer> getProductQuantities() {
        return PRODUCT_QUANTITIES;
    }
}
