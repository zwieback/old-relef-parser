package io.github.zwieback.relef.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_CATALOG")
public class Catalog extends BaseEntity {

    private static final long serialVersionUID = -4120555435017913704L;

    @Id
    @NotNull
    private Long id;

    @NotNull
    private String url;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CatalogLevel level;

    @Column(name = "parent_id", insertable = false, updatable = false)
    @Nullable
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Nullable
    private Catalog parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @NotFound(action = NotFoundAction.IGNORE)               // workaround to save tree of catalogs with not null id
    @NotNull
    private List<Catalog> children;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalog")
    @NotNull
    private List<Product> products;

    public Catalog() {
        id = 0L;
        url = "";
        name = "";
        level = CatalogLevel.NONE;
        children = new ArrayList<>();
        products = new ArrayList<>();
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public Catalog setId(@NotNull Long id) {
        this.id = id;
        return this;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public Catalog setUrl(@NotNull String url) {
        this.url = url;
        return this;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public Catalog setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public CatalogLevel getLevel() {
        return level;
    }

    public Catalog setLevel(@NotNull CatalogLevel level) {
        this.level = level;
        return this;
    }

    @Nullable
    public Long getParentId() {
        return parentId;
    }

    public Catalog setParentId(@Nullable Long parentId) {
        this.parentId = parentId;
        return this;
    }

    @Nullable
    public Catalog getParent() {
        return parent;
    }

    public Catalog setParent(@Nullable Catalog parent) {
        this.parent = parent;
        return this;
    }

    @NotNull
    public List<Catalog> getChildren() {
        return children;
    }

    public Catalog setChildren(@NotNull List<Catalog> children) {
        this.children = children;
        return this;
    }

    @NotNull
    public List<Product> getProducts() {
        return products;
    }

    public Catalog setProducts(@NotNull List<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalog catalog = (Catalog) o;

        return id.equals(catalog.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
