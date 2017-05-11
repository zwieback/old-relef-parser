package io.github.zwieback.relef.parsers;

import java.util.Collections;
import java.util.List;

public class Catalog70126ParserTest extends AbstractCatalogParserTest {

    private static final List<Integer> PRODUCT_QUANTITIES = Collections.singletonList(7);

    @Override
    Long getCatalogId() {
        return 70126L;
    }

    @Override
    int getPageCount() {
        return 1;
    }

    @Override
    List<Integer> getProductQuantities() {
        return PRODUCT_QUANTITIES;
    }
}
