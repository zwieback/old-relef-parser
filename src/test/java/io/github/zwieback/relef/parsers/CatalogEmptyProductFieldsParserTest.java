package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;

public class CatalogEmptyProductFieldsParserTest extends AbstractCatalogParserTest {

    private static final List<Integer> PRODUCT_QUANTITIES = Collections.singletonList(1);

    @Override
    Long getCatalogId() {
        return 1L;
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
    void validateParseProducts(Document catalogDocument, int productQuantity) {
        List<Product> products = catalogParser.parseProducts(catalogDocument, getCatalogId());
        products.forEach(product -> {
            assertNull(product.getArticle());
            assertNull(product.getManufacturer());
            assertNull(product.getTradeMark());
            assertNull(product.getParty());
        });
    }
}
