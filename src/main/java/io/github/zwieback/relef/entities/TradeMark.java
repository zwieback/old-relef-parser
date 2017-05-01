package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class TradeMark extends BaseEntity {

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

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "TradeMark{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
