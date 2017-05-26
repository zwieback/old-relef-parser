package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Manufacturer;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.ProductProperty;
import io.github.zwieback.relef.entities.TradeMark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ProductMergerTest extends AbstractMergerTest<Product, Long> {

    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final String STR_ONE = "ONE";
    private static final String STR_TWO = "TWO";
    private static final Double DOUBLE_ONE = 1.0;
    private static final Double DOUBLE_TWO = 2.0;
    private static final UUID UUID_ONE = UUID.randomUUID();
    private static final UUID UUID_TWO = UUID.randomUUID();
    private static final BigDecimal PRICE_ONE = BigDecimal.ONE;
    private static final BigDecimal PRICE_TWO = BigDecimal.TEN;
    private static final Integer INT_ONE = 1;
    private static final Integer INT_TWO = 2;
    private static final Boolean BOOL_ONE = Boolean.TRUE;
    private static final Boolean BOOL_TWO = Boolean.FALSE;

    @SuppressWarnings("unused")
    @Autowired
    private ProductMerger productMerger;

    @Override
    Merger<Product, Long> getMerger() {
        return productMerger;
    }

    @Override
    Product buildEntityStubOne() {
        return buildEntityStub(ID_ONE, ID_ONE, STR_ONE, STR_ONE, STR_ONE, STR_ONE, STR_ONE, STR_ONE,
                STR_ONE, STR_ONE, STR_ONE, null, null, STR_ONE,
                DOUBLE_ONE, DOUBLE_ONE, UUID_ONE, STR_ONE, PRICE_ONE, INT_ONE, INT_ONE, PRICE_ONE, BOOL_ONE,
                buildEmptyPropertyList());
    }

    @Override
    Product buildEntityStubTwo() {
        return buildEntityStub(ID_TWO, ID_TWO, STR_TWO, STR_TWO, STR_TWO, STR_TWO,
                STR_TWO, STR_TWO, STR_TWO, STR_TWO, STR_TWO, new Manufacturer(), new TradeMark(), STR_TWO,
                DOUBLE_TWO, DOUBLE_TWO, UUID_TWO, STR_TWO, PRICE_TWO, INT_TWO, INT_TWO, PRICE_TWO, BOOL_TWO,
                buildEmptyPropertyList());
    }

    @Override
    Product buildEntityStubOnePlusTwo() {
        return buildEntityStub(ID_ONE, ID_TWO, STR_TWO, STR_TWO, STR_TWO, STR_TWO,
                STR_TWO, STR_TWO, STR_TWO, STR_TWO, STR_TWO, new Manufacturer(), new TradeMark(), STR_TWO,
                DOUBLE_TWO, DOUBLE_TWO, UUID_TWO, STR_TWO, PRICE_TWO, INT_TWO, INT_TWO, PRICE_TWO, BOOL_TWO,
                buildEmptyPropertyList());
    }

    private static Product buildEntityStub(@NotNull Long id,
                                           @NotNull Long catalogId,
                                           @Nullable String code,
                                           @Nullable String article,
                                           @Nullable String barcode,
                                           @Nullable String manufacturerCountry,
                                           @Nullable String name,
                                           @Nullable String description,
                                           @NotNull String url,
                                           @Nullable String photoUrl,
                                           @Nullable String photoCachedUrl,
                                           @Nullable Manufacturer manufacturer,
                                           @Nullable TradeMark tradeMark,
                                           @Nullable String party,
                                           @Nullable Double weight,
                                           @Nullable Double volume,
                                           @Nullable UUID xmlId,
                                           @Nullable String dataType,
                                           @Nullable BigDecimal price,
                                           @Nullable Integer amount,
                                           @Nullable Integer available,
                                           @Nullable BigDecimal oldPrice,
                                           @Nullable Boolean blackFriday,
                                           @NotNull List<ProductProperty> properties) {
        return new Product()
                .setId(id)
                .setCatalogId(catalogId)
                .setCode(code)
                .setArticle(article)
                .setBarcode(barcode)
                .setManufacturerCountry(manufacturerCountry)
                .setName(name)
                .setDescription(description)
                .setUrl(url)
                .setPhotoUrl(photoUrl)
                .setPhotoCachedUrl(photoCachedUrl)
                .setManufacturer(manufacturer)
                .setTradeMark(tradeMark)
                .setParty(party)
                .setWeight(weight)
                .setVolume(volume)
                .setXmlId(xmlId)
                .setDataType(dataType)
                .setPrice(price)
                .setAmount(amount)
                .setAvailable(available)
                .setOldPrice(oldPrice)
                .setBlackFriday(blackFriday)
                .setProperties(properties);
    }

    @NotNull
    private static List<ProductProperty> buildEmptyPropertyList() {
        return new ArrayList<>();
    }

    @Override
    void shouldBeEquals(Product mergedEntity, Product parsedEntity) {
        assertEquals(mergedEntity.getId(), parsedEntity.getId());
        assertEquals(mergedEntity.getCatalogId(), parsedEntity.getCatalogId());
        assertEquals(mergedEntity.getCode(), parsedEntity.getCode());
        assertEquals(mergedEntity.getArticle(), parsedEntity.getArticle());
        assertEquals(mergedEntity.getBarcode(), parsedEntity.getBarcode());
        assertEquals(mergedEntity.getManufacturerCountry(), parsedEntity.getManufacturerCountry());
        assertEquals(mergedEntity.getName(), parsedEntity.getName());
        assertEquals(mergedEntity.getDescription(), parsedEntity.getDescription());
        assertEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
        assertEquals(mergedEntity.getPhotoUrl(), parsedEntity.getPhotoUrl());
        assertEquals(mergedEntity.getPhotoCachedUrl(), parsedEntity.getPhotoCachedUrl());
        assertEquals(mergedEntity.getManufacturer(), parsedEntity.getManufacturer());
        assertEquals(mergedEntity.getTradeMark(), parsedEntity.getTradeMark());
        assertEquals(mergedEntity.getParty(), parsedEntity.getParty());
        assertEquals(mergedEntity.getWeight(), parsedEntity.getWeight());
        assertEquals(mergedEntity.getVolume(), parsedEntity.getVolume());
        assertEquals(mergedEntity.getXmlId(), parsedEntity.getXmlId());
        assertEquals(mergedEntity.getDataType(), parsedEntity.getDataType());
        assertEquals(mergedEntity.getPrice(), parsedEntity.getPrice());
        assertEquals(mergedEntity.getAmount(), parsedEntity.getAmount());
        assertEquals(mergedEntity.getAvailable(), parsedEntity.getAvailable());
        assertEquals(mergedEntity.getOldPrice(), parsedEntity.getOldPrice());
        assertEquals(mergedEntity.getBlackFriday(), parsedEntity.getBlackFriday());
        assertEquals(mergedEntity.getProperties().size(), parsedEntity.getProperties().size());
    }

    @Override
    void shouldBeNotEquals(Product mergedEntity, Product parsedEntity) {
        assertNotEquals(mergedEntity.getId(), parsedEntity.getId());
        assertNotEquals(mergedEntity.getCatalogId(), parsedEntity.getCatalogId());
        assertNotEquals(mergedEntity.getCode(), parsedEntity.getCode());
        assertNotEquals(mergedEntity.getArticle(), parsedEntity.getArticle());
        assertNotEquals(mergedEntity.getBarcode(), parsedEntity.getBarcode());
        assertNotEquals(mergedEntity.getManufacturerCountry(), parsedEntity.getManufacturerCountry());
        assertNotEquals(mergedEntity.getName(), parsedEntity.getName());
        assertNotEquals(mergedEntity.getDescription(), parsedEntity.getDescription());
        assertNotEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
        assertNotEquals(mergedEntity.getPhotoUrl(), parsedEntity.getPhotoUrl());
        assertNotEquals(mergedEntity.getPhotoCachedUrl(), parsedEntity.getPhotoCachedUrl());
        assertNotEquals(mergedEntity.getManufacturer(), parsedEntity.getManufacturer());
        assertNotEquals(mergedEntity.getTradeMark(), parsedEntity.getTradeMark());
        assertNotEquals(mergedEntity.getParty(), parsedEntity.getParty());
        assertNotEquals(mergedEntity.getWeight(), parsedEntity.getWeight());
        assertNotEquals(mergedEntity.getVolume(), parsedEntity.getVolume());
        assertNotEquals(mergedEntity.getXmlId(), parsedEntity.getXmlId());
        assertNotEquals(mergedEntity.getDataType(), parsedEntity.getDataType());
        assertNotEquals(mergedEntity.getPrice(), parsedEntity.getPrice());
        assertNotEquals(mergedEntity.getAmount(), parsedEntity.getAmount());
        assertNotEquals(mergedEntity.getAvailable(), parsedEntity.getAvailable());
        assertNotEquals(mergedEntity.getOldPrice(), parsedEntity.getOldPrice());
        assertNotEquals(mergedEntity.getBlackFriday(), parsedEntity.getBlackFriday());
    }
}
