package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_TRADE_MARK")
public class TradeMark extends BaseEntity {

    private static final long serialVersionUID = 8861559741783219433L;

    @Id
    @NotNull
    private String name;

    @NotNull
    private String url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tradeMark")
    private List<Product> products;

    public TradeMark() {
        name = "";
        url = "";
        products = new ArrayList<>();
    }

    public TradeMark(@NotNull String name, @NotNull String url) {
        this.name = name;
        this.url = url;
        this.products = new ArrayList<>();
    }

    @NotNull
    public String getName() {
        return name;
    }

    public TradeMark setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public TradeMark setUrl(@NotNull String url) {
        this.url = url;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public TradeMark setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeMark tradeMark = (TradeMark) o;
        return name.equals(tradeMark.name) && url.equals(tradeMark.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TradeMark{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
