package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CatalogEmptyProductFields2ParserTest extends AbstractCatalogParserTest {

    private static final List<Integer> PRODUCT_QUANTITIES = Collections.singletonList(1);

    @Override
    Long getCatalogId() {
        return 2L;
    }

    @Override
    int getPageCount() {
        return 1;
    }

    @Override
    List<Integer> getProductQuantities() {
        return PRODUCT_QUANTITIES;
    }

    void validateParseProducts(Document catalogDocument, int productQuantity) {
        List<Product> products = catalogParser.parseProducts(catalogDocument, getCatalogId());
        products.forEach(product -> {
            assertNull(product.getArticle());
            assertNull(product.getParty());
            assertEquals(12, product.getProperties().size());
        });
    }
}
