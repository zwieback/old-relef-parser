package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;
import org.jsoup.nodes.Document;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Override
    void validateParseProducts(Document catalogDocument, int productQuantity) {
        List<Product> products = catalogParser.parseProducts(catalogDocument, getCatalogId());
        products.forEach(product -> {
            assertThat(product.getArticle()).isNull();
            assertThat(product.getParty()).isNull();
            assertThat(product.getProperties()).hasSize(12);
        });
    }
}
