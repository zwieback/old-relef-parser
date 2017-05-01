package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;

public class Product34259ParserTest extends AbstractProductParserTest {

    private static final Long CATALOG_ID = 68711L;
    private static final Long PRODUCT_ID = 34259L;

    @Override
    String getResourcePage() {
        return "classpath:pages/catalog_68711_product_34259.html";
    }

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
        return new Product()
                .setId(PRODUCT_ID)
                .setCatalogId(CATALOG_ID)
                .setPhotoUrl("http://relefopt.ru/upload/iblock/fc8/77e2290d_c30f_11dd_8aea_00e098c184c1_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setPhotoCachedUrl("http://relefopt.ru/upload/resize_cache/iblock/fc8/246_250_1/77e2290d_c30f_11dd_8aea_00e098c184c1_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setName("Доска магнитно-маркерная 90*120, алюминиевая рамка, полочка")
                .setDescription("Доска выполнена из стальной жести с покрытием из высококачественного пластика. " +
                        "Доска необходима при проведении совещаний, семинаров, собраний и презентаций. " +
                        "Писать на доске можно маркерами на водной основе, предназначенными для маркерных досок.")
                .setCode("114377")
                .setArticle("TF8010")
                .setBarcode("14260107451652")
                .setManufacturerCountry("китай")
                .setParty("1 / 1 / 1")
                .setWeight(6.00)
                .setVolume(0.036)
                .setProperties(generatePropertiesStub(10))
                .setManufacturer(generateManufacturer("Berlingo", "http://relefopt.ru/catalog/68711/?arrFilter_pf[DP_MANIFACTUR][12819243]=12819243"))
                .setTradeMark(generateTradeMark("Berlingo", "http://relefopt.ru/brands/12820028.php"));
    }
}
