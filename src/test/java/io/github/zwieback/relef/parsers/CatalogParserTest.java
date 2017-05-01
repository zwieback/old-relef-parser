package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.configs.DatabaseConfig;
import io.github.zwieback.relef.configs.ParserConfigForTest;
import io.github.zwieback.relef.configs.PropertyConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DatabaseConfig.class,
        ParserConfigForTest.class,
        PropertyConfig.class,
        ServiceConfig.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CatalogParserTest {

    private static final Long CATALOG_ID = 69472L;
    private static final int PAGE_COUNT = 9;
    private static final int HEADERS_ARGUMENT_INDEX = 1;
    private static final String PAGE_NUMBER_KEY = "PAGEN_1";

    private static final List<Integer> PRODUCT_QUANTITIES = Arrays.asList(48, 48, 48, 48, 48, 48, 48, 48, 20);

    @SuppressWarnings("unused")
    @Autowired
    private FileParser fileParser;

    @SuppressWarnings("unused")
    @Autowired
    private InternalParser internalParser;

    @SuppressWarnings("unused")
    @Autowired
    private ResourceLoader resourceLoader;

    @SuppressWarnings("unused")
    @Autowired
    private CatalogParser catalogParser;

    @Before
    public void setUp() {
        doAnswer(invocationOnMock -> {
            Headers headers = invocationOnMock.getArgumentAt(HEADERS_ARGUMENT_INDEX, Headers.class);
            String pageNumber = headers.get(PAGE_NUMBER_KEY);
            return getCatalogDocument(Integer.valueOf(pageNumber));
        }).when(internalParser).post(anyString(), anyObject());
    }

    private Document getCatalogDocument(int pageNumber) throws IOException {
        String resourcePage = String.format("classpath:pages/catalog_%d_page_%d.html", CATALOG_ID, pageNumber);
        Resource resource = resourceLoader.getResource(resourcePage);
        InputStream inputStream = resource.getInputStream();
        return fileParser.parseInputStream(inputStream);
    }

    @Test
    public void test_parseUrl_should_returns_not_empty_catalog_documents() {
        List<Document> catalogDocuments = catalogParser.parseUrl("");
        assertEquals(catalogDocuments.size(), PAGE_COUNT);
    }

    @Test
    public void test_parseProductUrls_should_returns_parsed_product_urls() {
        List<Document> catalogDocuments = catalogParser.parseUrl("");
        assertEquals(catalogDocuments.size(), PRODUCT_QUANTITIES.size());

        IntStream.range(0, catalogDocuments.size())
                .forEach(i -> validateParseProductUrls(catalogDocuments.get(i), PRODUCT_QUANTITIES.get(i)));
    }

    private void validateParseProductUrls(Document catalogDocument, int productQuantity) {
        List<String> productUrls = catalogParser.parseProductUrls(catalogDocument);
        assertEquals(productUrls.size(), productQuantity);
    }

    @Test
    public void test_parseProducts_should_returns_parsed_products() {
        List<Document> catalogDocuments = catalogParser.parseUrl("");
        assertEquals(catalogDocuments.size(), PRODUCT_QUANTITIES.size());

        IntStream.range(0, catalogDocuments.size())
                .forEach(i -> validateParseProducts(catalogDocuments.get(i), PRODUCT_QUANTITIES.get(i)));
    }

    private void validateParseProducts(Document catalogDocument, int productQuantity) {
        List<Product> products = catalogParser.parseProducts(catalogDocument, CATALOG_ID);
        assertEquals(products.size(), productQuantity);
    }
}
