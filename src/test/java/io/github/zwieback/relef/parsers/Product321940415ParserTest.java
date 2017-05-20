package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Product;

import java.util.UUID;

public class Product321940415ParserTest extends AbstractProductParserTest {

    private static final Long CATALOG_ID = 69472L;
    private static final Long PRODUCT_ID = 321940415L;

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
                .setPhotoUrl("http://relefopt.ru/upload/iblock/7e6/397de5b3_0fa3_11e7_9405_acf1df3ff64b_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setPhotoCachedUrl("http://relefopt.ru/upload/resize_cache/iblock/7e6/246_250_1/397de5b3_0fa3_11e7_9405_acf1df3ff64b_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg")
                .setName("Альбом для рисования 48л. А4 на гребне \"Техника. Bon voyage\"")
                .setDescription("Альбом для рисования формата А4 в обложке из импортного мелованного картона. " +
                        "Внутренний блок из 48 листов офсетной бумаги на двойной евроспирали. " +
                        "Плотность бумаги — 100 г/кв.м, соответствует Техническому регламенту " +
                        "«О безопасности продукции, предназначенной для детей и подростков». " +
                        "Подходит для рисования различными типами красок, фломастерами, цветными и " +
                        "чернографитными карандашами, гелевыми ручками. " +
                        "Отделка обложки – ВД-лак.")
                .setCode("246122")
                .setArticle("А48сп_14496")
                .setBarcode("4680211124960")
                .setManufacturerCountry("россия")
                .setParty("5 / 10 / 20")
                .setWeight(0.51)
                .setVolume(0.001)
                .setXmlId(UUID.fromString("397de5b3-0fa3-11e7-9405-acf1df3ff64b"))
                .setDataType("basket")
                .setAmount(5)
                .setProperties(generatePropertiesStub(22))
                .setManufacturer(generateManufacturer("Спейс", "http://relefopt.ru/catalog/69472/?arrFilter_pf[DP_MANIFACTUR][12861056]=12861056"))
                .setTradeMark(generateTradeMark("ArtSpace", "http://relefopt.ru/brands/12908290.php"));
    }
}
