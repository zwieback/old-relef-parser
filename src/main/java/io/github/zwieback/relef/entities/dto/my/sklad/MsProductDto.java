package io.github.zwieback.relef.entities.dto.my.sklad;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

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
                        @Nullable Double volume) {
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
    }

    @Nullable
    public String getCatalogName() {
        return catalogName;
    }

    public MsProductDto setCatalogName(@Nullable String catalogName) {
        this.catalogName = catalogName;
        return this;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    public MsProductDto setCode(@Nullable String code) {
        this.code = code;
        return this;
    }

    @Nullable
    public String getExternalCode() {
        return externalCode;
    }

    public MsProductDto setExternalCode(@Nullable String externalCode) {
        this.externalCode = externalCode;
        return this;
    }

    @Nullable
    public String getArticle() {
        return article;
    }

    public MsProductDto setArticle(@Nullable String article) {
        this.article = article;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public MsProductDto setName(@Nullable String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public MsProductDto setDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    @Nullable
    public String getUnit() {
        return unit;
    }

    public MsProductDto setUnit(@Nullable String unit) {
        this.unit = unit;
        return this;
    }

    @Nullable
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public MsProductDto setSellingPrice(@Nullable BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    @Nullable
    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public MsProductDto setOldPrice(@Nullable BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    @Nullable
    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public MsProductDto setNewPrice(@Nullable BigDecimal newPrice) {
        this.newPrice = newPrice;
        return this;
    }

    @Nullable
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public MsProductDto setMinPrice(@Nullable BigDecimal minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    @Nullable
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public MsProductDto setPurchasePrice(@Nullable BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    @Nullable
    public String getBarcodeEan13() {
        return barcodeEan13;
    }

    public MsProductDto setBarcodeEan13(@Nullable String barcodeEan13) {
        this.barcodeEan13 = barcodeEan13;
        return this;
    }

    @Nullable
    public String getBarcodeEan8() {
        return barcodeEan8;
    }

    public MsProductDto setBarcodeEan8(@Nullable String barcodeEan8) {
        this.barcodeEan8 = barcodeEan8;
        return this;
    }

    @Nullable
    public String getBarcodeCode128() {
        return barcodeCode128;
    }

    public MsProductDto setBarcodeCode128(@Nullable String barcodeCode128) {
        this.barcodeCode128 = barcodeCode128;
        return this;
    }

    @Nullable
    public String getManufacturerCountry() {
        return manufacturerCountry;
    }

    public MsProductDto setManufacturerCountry(@Nullable String manufacturerCountry) {
        this.manufacturerCountry = manufacturerCountry;
        return this;
    }

    @Nullable
    public String getManufacturer() {
        return manufacturer;
    }

    public MsProductDto setManufacturer(@Nullable String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Nullable
    public String getProvider() {
        return provider;
    }

    public MsProductDto setProvider(@Nullable String provider) {
        this.provider = provider;
        return this;
    }

    @Nullable
    public Double getWeight() {
        return weight;
    }

    public MsProductDto setWeight(@Nullable Double weight) {
        this.weight = weight;
        return this;
    }

    @Nullable
    public Double getVolume() {
        return volume;
    }

    public MsProductDto setVolume(@Nullable Double volume) {
        this.volume = volume;
        return this;
    }

    @Nullable
    public Boolean getArchival() {
        return archival;
    }

    public MsProductDto setArchival(@Nullable Boolean archival) {
        this.archival = archival;
        return this;
    }

    @Nullable
    public String getModificationCode() {
        return modificationCode;
    }

    public MsProductDto setModificationCode(@Nullable String modificationCode) {
        this.modificationCode = modificationCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsProductDto that = (MsProductDto) o;
        return Objects.equals(catalogName, that.catalogName) &&
                Objects.equals(code, that.code) &&
                Objects.equals(externalCode, that.externalCode) &&
                Objects.equals(article, that.article) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(sellingPrice, that.sellingPrice) &&
                Objects.equals(oldPrice, that.oldPrice) &&
                Objects.equals(newPrice, that.newPrice) &&
                Objects.equals(minPrice, that.minPrice) &&
                Objects.equals(purchasePrice, that.purchasePrice) &&
                Objects.equals(barcodeEan13, that.barcodeEan13) &&
                Objects.equals(barcodeEan8, that.barcodeEan8) &&
                Objects.equals(barcodeCode128, that.barcodeCode128) &&
                Objects.equals(manufacturerCountry, that.manufacturerCountry) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(archival, that.archival) &&
                Objects.equals(modificationCode, that.modificationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogName, code, externalCode, article, name, description, unit, sellingPrice,
                oldPrice, newPrice, minPrice, purchasePrice, barcodeEan13, barcodeEan8, barcodeCode128,
                manufacturerCountry, manufacturer, provider, weight, volume, archival, modificationCode);
    }

    @Override
    public String toString() {
        return "MsProductDto{" +
                "catalogName='" + catalogName + '\'' +
                ", code='" + code + '\'' +
                ", externalCode='" + externalCode + '\'' +
                ", article='" + article + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", minPrice=" + minPrice +
                ", purchasePrice=" + purchasePrice +
                ", barcodeEan13='" + barcodeEan13 + '\'' +
                ", barcodeEan8='" + barcodeEan8 + '\'' +
                ", barcodeCode128='" + barcodeCode128 + '\'' +
                ", manufacturerCountry='" + manufacturerCountry + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", provider='" + provider + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", archival=" + archival +
                ", modificationCode='" + modificationCode + '\'' +
                '}';
    }
}
