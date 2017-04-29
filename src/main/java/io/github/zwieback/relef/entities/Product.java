package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.utils.StringFormatter.formatDouble;

public class Product {

    @NotNull
    private Integer id;

    @NotNull
    private Integer catalogId;

    // Код
    @Nullable
    private String code;

    // Артикул
    @Nullable
    private String article;

    // Штрихкод
    @Nullable
    private String barcode;

    // Страна производитель
    @Nullable
    private String manufacturerCountry;

    // Наименование
    @Nullable
    private String name;

    // Описание
    @Nullable
    private String description;

    // Ссылка на продукт
    @Nullable
    private String url;

    // Ссылка на фотографию
    @Nullable
    private String photoUrl;

    // Ссылка на кешированную фотографию
    @Nullable
    private String photoCachedUrl;

    // Производитель
    @Nullable
    private Manufacturer manufacturer;

    // Торговая марка
    @Nullable
    private TradeMark tradeMark;

    // Партия / упаковка
    @Nullable
    private String party;

    // Вес, кг
    @Nullable
    private Double weight;

    // Объем, м³
    @Nullable
    private Double volume;

    // Свойства
    @NotNull
    private List<ProductProperty> properties;

    public Product() {
        id = 0;
        catalogId = 0;
        properties = new ArrayList<>();
    }

    @NotNull
    public Integer getId() {
        return id;
    }

    public Product setId(@NotNull Integer id) {
        this.id = id;
        return this;
    }

    @NotNull
    public Integer getCatalogId() {
        return catalogId;
    }

    public Product setCatalogId(@NotNull Integer catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    public Product setCode(@Nullable String code) {
        this.code = code;
        return this;
    }

    @Nullable
    public String getArticle() {
        return article;
    }

    public Product setArticle(@Nullable String article) {
        this.article = article;
        return this;
    }

    @Nullable
    public String getBarcode() {
        return barcode;
    }

    public Product setBarcode(@Nullable String barcode) {
        this.barcode = barcode;
        return this;
    }

    @Nullable
    public String getManufacturerCountry() {
        return manufacturerCountry;
    }

    public Product setManufacturerCountry(@Nullable String manufacturerCountry) {
        this.manufacturerCountry = manufacturerCountry;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public Product setName(@Nullable String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public Product setDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public Product setUrl(@Nullable String url) {
        this.url = url;
        return this;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    public Product setPhotoUrl(@Nullable String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    @Nullable
    public String getPhotoCachedUrl() {
        return photoCachedUrl;
    }

    public Product setPhotoCachedUrl(@Nullable String photoCachedUrl) {
        this.photoCachedUrl = photoCachedUrl;
        return this;
    }

    @Nullable
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Product setManufacturer(@Nullable Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Nullable
    public TradeMark getTradeMark() {
        return tradeMark;
    }

    public Product setTradeMark(@Nullable TradeMark tradeMark) {
        this.tradeMark = tradeMark;
        return this;
    }

    @Nullable
    public String getParty() {
        return party;
    }

    public Product setParty(@Nullable String party) {
        this.party = party;
        return this;
    }

    @Nullable
    public Double getWeight() {
        return weight;
    }

    public Product setWeight(@Nullable Double weight) {
        this.weight = weight;
        return this;
    }

    @Nullable
    public Double getVolume() {
        return volume;
    }

    public Product setVolume(@Nullable Double volume) {
        this.volume = volume;
        return this;
    }

    @NotNull
    public List<ProductProperty> getProperties() {
        return properties;
    }

    public Product setProperties(@NotNull List<ProductProperty> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", catalogId=" + catalogId +
                ", code='" + code + '\'' +
                ", article='" + article + '\'' +
                ", barcode='" + barcode + '\'' +
                ", manufacturerCountry='" + manufacturerCountry + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", photoCachedUrl='" + photoCachedUrl + '\'' +
                ", manufacturer=" + manufacturer +
                ", tradeMark=" + tradeMark +
                ", party='" + party + '\'' +
                ", weight='" + formatDouble(weight) + '\'' +
                ", volume='" + formatDouble(volume) + '\'' +
                ", properties=" + properties +
                '}';
    }
}
