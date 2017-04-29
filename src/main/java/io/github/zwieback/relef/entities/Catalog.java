package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    @NotNull
    private Integer id;

    @NotNull
    private String url;

    @NotNull
    private String name;

    @NotNull
    private CatalogLevel level;

    @NotNull
    private List<Catalog> children;

    public Catalog() {
        id = 0;
        url = "";
        name = "";
        level = CatalogLevel.NONE;
        children = new ArrayList<>();
    }

    @NotNull
    public Integer getId() {
        return id;
    }

    public Catalog setId(@NotNull Integer id) {
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

    @NotNull
    public List<Catalog> getChildren() {
        return children;
    }

    public Catalog setChildren(@NotNull List<Catalog> children) {
        this.children = children;
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
