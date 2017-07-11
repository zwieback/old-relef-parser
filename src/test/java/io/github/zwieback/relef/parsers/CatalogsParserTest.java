package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogsParserTest extends AbstractParserTest {

    private static final EnumMap<CatalogLevel, Integer> CATALOG_LEVEL_SIZE_MAP = initializeCatalogLevelSizeMap();

    private static EnumMap<CatalogLevel, Integer> initializeCatalogLevelSizeMap() {
        EnumMap<CatalogLevel, Integer> map = new EnumMap<>(CatalogLevel.class);
        map.put(CatalogLevel.FIRST, 18);
        map.put(CatalogLevel.SECOND, 119);
        map.put(CatalogLevel.THIRD, 442);
        map.put(CatalogLevel.FOURTH, 711);
        return map;
    }

    @SuppressWarnings("unused")
    @Autowired
    private CatalogsParser catalogsParser;

    @Override
    String getResourcePage() {
        return "classpath:pages/catalogs.html";
    }

    @Test
    public void test_parseBrands_should_returns_parsed_brands() {
        Document catalogsDocument = catalogsParser.parseUrl("");
        List<Brand> brands = catalogsParser.parseBrands(catalogsDocument);
        assertThat(brands).hasSize(83);
    }

    @Test
    public void test_parseCatalogsOfLevel_should_returns_parsed_catalogs() {
        CATALOG_LEVEL_SIZE_MAP.forEach(this::validateParseCatalogsOfLevel);
    }

    private void validateParseCatalogsOfLevel(CatalogLevel level, int expectedSize) {
        Document catalogsDocument = catalogsParser.parseUrl("");
        List<Catalog> catalogs = catalogsParser.parseCatalogsOfLevel(catalogsDocument, level);
        assertThat(catalogs).hasSize(expectedSize);
    }

    @Test
    public void test_parseTreeOfCatalogs_should_returns_parsed_tree_of_catalogs() {
        CATALOG_LEVEL_SIZE_MAP.forEach(this::validateParseTreeOfCatalogs);
    }

    private void validateParseTreeOfCatalogs(CatalogLevel level, int expectedSize) {
        Document catalogsDocument = catalogsParser.parseUrl("");
        List<Catalog> catalogs = catalogsParser.parseTreeOfCatalogs(catalogsDocument, level);
        assertThat(catalogs).hasSize(expectedSize);
    }

    @Test
    public void test_parseCatalogUrls_should_returns_parsed_urls_of_catalogs() {
        CATALOG_LEVEL_SIZE_MAP.forEach(this::validateParseCatalogUrls);
    }

    private void validateParseCatalogUrls(CatalogLevel level, int expectedSize) {
        Document catalogsDocument = catalogsParser.parseUrl("");
        List<String> catalogs = catalogsParser.parseCatalogUrls(catalogsDocument, level);
        assertThat(catalogs).hasSize(expectedSize);
    }
}
