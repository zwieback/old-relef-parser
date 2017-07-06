package io.github.zwieback.relef.entities.dto.my.sklad;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

@Data
public class MsProductDto {

    // Каталог
    @Nullable
    private String catalogName;

    // Код
    @Nullable
    private String code;

    // Внешний код
    @Nullable
    private String externalCode;

    // Артикул
    @Nullable
    private String article;

    // Наименование
    @Nullable
    private String name;

    // Описание
    @Nullable
    private String description;

    // Единица измерения
    @Nullable
    private String unit;

    // Цена продажи
    @Nullable
    private BigDecimal sellingPrice;

    // Старая цена
    @Nullable
    private BigDecimal oldPrice;

    // Новая цена
    @Nullable
    private BigDecimal newPrice;

    // Минимальная цена
    @Nullable
    private BigDecimal minPrice;

    // Закупочная цена
    @Nullable
    private BigDecimal purchasePrice;

    // Штрихкод EAN13
    @Nullable
    private String barcodeEan13;

    // Штрихкод EAN8
    @Nullable
    private String barcodeEan8;

    // Штрихкод Code128
    @Nullable
    private String barcodeCode128;

    // Страна производитель
    @Nullable
    private String manufacturerCountry;

    // Производитель
    @Nullable
    private String manufacturer;

    // Поставщик
    @Nullable
    private String provider;

    // Вес, кг
    @Nullable
    private Double weight;

    // Объем, м³
    @Nullable
    private Double volume;

    // Архивный
    @Nullable
    private Boolean archival;

    // Код модификации
    @Nullable
    private String modificationCode;

    // URL на изображение
    @Nullable
    private String photoUrl;

    public MsProductDto() {
    }

    public MsProductDto(@Nullable String catalogName,
                        @Nullable String code,
                        @Nullable String article,
                        @Nullable String name,
                        @Nullable String description,
                        @Nullable BigDecimal purchasePrice,
                        @Nullable BigDecimal oldPrice,
                        @Nullable String barcodeEan13,
                        @Nullable String manufacturerCountry,
                        @Nullable String manufacturer,
                        @Nullable String provider,
                        @Nullable Double weight,
                        @Nullable Double volume,
                        @Nullable String photoUrl) {
        this.catalogName = catalogName;
        this.code = code;
        this.article = article;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.oldPrice = oldPrice;
        this.barcodeEan13 = barcodeEan13;
        this.manufacturerCountry = manufacturerCountry;
        this.manufacturer = manufacturer;
        this.provider = provider;
        this.weight = weight;
        this.volume = volume;
        this.photoUrl = photoUrl;
    }
}
