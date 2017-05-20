package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;

import java.util.UUID;

public class Product19298702ParserTest extends AbstractProductParserTest {

    private static final Long CATALOG_ID = 64323L;
    private static final Long PRODUCT_ID = 19298702L;

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
                .setPhotoUrl("http://relefopt.ru/upload/iblock/b94/e8917aa4_531b_11e1_9aff_d8d385e1136a_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setPhotoCachedUrl("http://relefopt.ru/upload/resize_cache/iblock/b94/246_250_1/e8917aa4_531b_11e1_9aff_d8d385e1136a_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setName("ПО ESET NOD32 Smart Security - продление лицензии на 1 год (NOD32-ESS-RN-BOX-1-1)")
                .setDescription("Тип ПО Продление лицензии на 1 год " +
                        "Тип лицензии BOX " +
                        "Область применения Домашнее применение " +
                        "Срок действия 1 год " +
                        "Язык интерфейса Русский " +
                        "Количество защищаемых компьютеров 1")
                .setCode("164879")
                .setArticle("NOD32-ESS-RN-BOX-1-1")
                .setBarcode("4612744310046")
                .setManufacturerCountry("словакия")
                .setParty("1 / 1 / 1")
                .setWeight(0.13)
                .setVolume(0.002)
                .setXmlId(UUID.fromString("e8917aa4-531b-11e1-9aff-d8d385e1136a"))
                .setDataType("basket")
                .setAmount(1)
                .setProperties(generatePropertiesStub(4))
                .setManufacturer(generateManufacturer("Eset", "http://relefopt.ru/catalog/64323/?arrFilter_pf[DP_MANIFACTUR][64031]=64031"))
                .setTradeMark(generateTradeMark("NOD32", "http://relefopt.ru/brands/64033.php"));
    }
}
