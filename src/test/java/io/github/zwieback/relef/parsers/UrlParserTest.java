package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.configs.ParserConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import io.github.zwieback.relef.parsers.exceptions.UrlParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ParserConfig.class,
        ServiceConfig.class
})
public class UrlParserTest {

    private static final String INCORRECT_CATALOG_URL = "";
    private static final String CORRECT_CATALOG_URL = "http://relefopt.ru/catalog/69472/";
    private static final Long CORRECT_CATALOG_ID = 69472L;

    private static final String INCORRECT_PRODUCT_URL = "";
    private static final String CORRECT_PRODUCT_URL = CORRECT_CATALOG_URL + "321940415/";
    private static final Long CORRECT_PRODUCT_ID = 321940415L;

    private static final String INCORRECT_BRAND_URL = "";
    private static final String CORRECT_BRAND_URL = "http://relefopt.ru/brands/48921/";
    private static final Long CORRECT_BRAND_ID = 48921L;

    @SuppressWarnings("unused")
    @Autowired
    private UrlParser urlParser;


    @Test(expected = UrlParseException.class)
    public void test_parseCatalogIdFromCatalogUrl_should_throws_urlParseException() {
        urlParser.parseCatalogIdFromCatalogUrl(INCORRECT_CATALOG_URL);
    }

    @Test
    public void test_parseCatalogIdFromCatalogUrl_should_returns_correct_catalogId() {
        Long catalogId = urlParser.parseCatalogIdFromCatalogUrl(CORRECT_CATALOG_URL);
        assertEquals(CORRECT_CATALOG_ID, catalogId);
    }

    @Test(expected = UrlParseException.class)
    public void test_parseCatalogIdFromProductUrl_should_throws_urlParseException() {
        urlParser.parseCatalogIdFromProductUrl(INCORRECT_PRODUCT_URL);
    }

    @Test
    public void test_parseCatalogIdFromProductUrl_should_returns_correct_catalogId() {
        Long catalogId = urlParser.parseCatalogIdFromProductUrl(CORRECT_PRODUCT_URL);
        assertEquals(CORRECT_CATALOG_ID, catalogId);
    }

    @Test(expected = UrlParseException.class)
    public void test_parseProductIdFromProductUrl_should_throws_urlParseException() {
        urlParser.parseProductIdFromProductUrl(INCORRECT_PRODUCT_URL);
    }

    @Test
    public void test_parseProductIdFromProductUrl_should_returns_correct_productId() {
        Long productId = urlParser.parseProductIdFromProductUrl(CORRECT_PRODUCT_URL);
        assertEquals(CORRECT_PRODUCT_ID, productId);
    }

    @Test(expected = UrlParseException.class)
    public void test_parseBrandId_should_throws_urlParseException() {
        urlParser.parseBrandId(INCORRECT_BRAND_URL);
    }

    @Test
    public void test_parseBrandId_should_returns_correct_brandId() {
        Long brandId = urlParser.parseBrandId(CORRECT_BRAND_URL);
        assertEquals(CORRECT_BRAND_ID, brandId);
    }
}
