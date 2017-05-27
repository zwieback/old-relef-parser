package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_MANUFACTURER")
public class Manufacturer extends BaseEntity {

    @Id
    @NotNull
    private String name;

    @NotNull
    private String url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacturer")
    private List<Product> products;

    public Manufacturer() {
        name = "";
        url = "";
        products = new ArrayList<>();
    }

    public Manufacturer(@NotNull String name, @NotNull String url) {
        this.name = name;
        this.url = url;
        products = new ArrayList<>();
    }

    @NotNull
    public String getName() {
        return name;
    }

    public Manufacturer setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public Manufacturer setUrl(@NotNull String url) {
        this.url = url;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Manufacturer setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return name.equals(that.name) && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
