package io.github.zwieback.relef.entities;

import com.google.common.base.MoreObjects;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static io.github.zwieback.relef.utils.StringFormatter.formatDouble;

@Data
@Entity
@Table(name = "T_PRODUCT")
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1008672614485818576L;
    private static final int MAX_LENGTH_OF_DESCRIPTION = 4000;

    @Id
    @NotNull
    private Long id = 0L;

    @Column(name = "catalog_id")
    @NotNull
    private Long catalogId = 0L;

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
    private String url = "";

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
    private List<ProductProperty> properties = new ArrayList<>();

    public Product setDescription(@Nullable String description) {
        this.description = description;
        if (description != null && description.length() > MAX_LENGTH_OF_DESCRIPTION) {
            this.description = description.substring(0, MAX_LENGTH_OF_DESCRIPTION);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("catalogId", catalogId)
                .add("code", code)
                .add("article", article)
                .add("barcode", barcode)
                .add("manufacturerCountry", manufacturerCountry)
                .add("name", name)
                .add("description", description)
                .add("url", url)
                .add("photoUrl", photoUrl)
                .add("photoCachedUrl", photoCachedUrl)
                .add("manufacturer", manufacturer)
                .add("tradeMark", tradeMark)
                .add("party", party)
                .add("weight", formatDouble(weight))
                .add("volume", formatDouble(volume))
                .add("xmlId", xmlId)
                .add("dataType", dataType)
                .add("price", price)
                .add("amount", amount)
                .add("available", available)
                .add("oldPrice", oldPrice)
                .add("blackFriday", blackFriday)
                .add("properties", properties)
                .toString();
    }
}
