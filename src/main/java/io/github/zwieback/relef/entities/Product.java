package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.github.zwieback.relef.utils.StringFormatter.formatDouble;

@Entity
@Table(name = "T_PRODUCT")
public class Product extends BaseEntity {

    private static final int MAX_LENGTH_OF_DESCRIPTION = 4000;

    @Id
    @NotNull
    private Long id;

    @Column(name = "catalog_id")
    @NotNull
    private Long catalogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", insertable = false, updatable = false)
    @Nullable
    private Catalog catalog;

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
    @Column(name = "manufacturer_country")
    @Nullable
    private String manufacturerCountry;

    // Наименование
    @Nullable
    private String name;

    // Описание
    @Nullable
    private String description;

    // Ссылка на продукт
    @NotNull
    private String url;

    // Ссылка на фотографию
    @Column(name = "photo_url")
    @Nullable
    private String photoUrl;

    // Ссылка на кешированную фотографию
    @Column(name = "photo_cached_url")
    @Nullable
    private String photoCachedUrl;

    // Производитель
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_name")
    @Nullable
    private Manufacturer manufacturer;

    // Торговая марка
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trade_mark_name")
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

    // XML ID
    // чаще всего используется для поиска цены продуктов
    @Column(name = "xml_id")
    @Nullable
    private UUID xmlId;

    // Тип продукта
    // чаще всего используется для поиска цены продуктов
    @Column(name = "data_type")
    @Nullable
    private String dataType;

    // Цена
    @Nullable
    private BigDecimal price;

    // ?
    @Nullable
    private Integer amount;

    // Доступное количество
    @Nullable
    private Integer available;

    // Старая цена
    @Column(name = "old_price")
    @Nullable
    private BigDecimal oldPrice;

    // ?
    @Column(name = "black_friday")
    @Nullable
    private Boolean blackFriday;

    // Свойства
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    @NotNull
    private List<ProductProperty> properties;

    public Product() {
        id = 0L;
        catalogId = 0L;
        url = "";
        properties = new ArrayList<>();
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public Product setId(@NotNull Long id) {
        this.id = id;
        return this;
    }

    @NotNull
    public Long getCatalogId() {
        return catalogId;
    }

    public Product setCatalogId(@NotNull Long catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    @Nullable
    public Catalog getCatalog() {
        return catalog;
    }

    public Product setCatalog(@Nullable Catalog catalog) {
        this.catalog = catalog;
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
        if (description != null && description.length() > MAX_LENGTH_OF_DESCRIPTION) {
            this.description = description.substring(0, MAX_LENGTH_OF_DESCRIPTION);
        }
        return this;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public Product setUrl(@NotNull String url) {
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

    @Nullable
    public UUID getXmlId() {
        return xmlId;
    }

    public Product setXmlId(@Nullable UUID xmlId) {
        this.xmlId = xmlId;
        return this;
    }

    @Nullable
    public String getDataType() {
        return dataType;
    }

    public Product setDataType(@Nullable String dataType) {
        this.dataType = dataType;
        return this;
    }

    @Nullable
    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(@Nullable BigDecimal price) {
        this.price = price;
        return this;
    }

    @Nullable
    public Integer getAmount() {
        return amount;
    }

    public Product setAmount(@Nullable Integer amount) {
        this.amount = amount;
        return this;
    }

    @Nullable
    public Integer getAvailable() {
        return available;
    }

    public Product setAvailable(@Nullable Integer available) {
        this.available = available;
        return this;
    }

    @Nullable
    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public Product setOldPrice(@Nullable BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    @Nullable
    public Boolean getBlackFriday() {
        return blackFriday;
    }

    public Product setBlackFriday(@Nullable Boolean blackFriday) {
        this.blackFriday = blackFriday;
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
