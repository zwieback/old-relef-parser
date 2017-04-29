package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Brand {

    @NotNull
    private Integer id;

    @Nullable
    private String name;

    @Nullable
    private String url;

    @Nullable
    private String imageUrl;

    public Brand() {
        id = 0;
    }

    public Brand(@NotNull Integer id, @Nullable String url, @Nullable String imageUrl) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    @NotNull
    public Integer getId() {
        return id;
    }

    public Brand setId(@NotNull Integer id) {
        this.id = id;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public Brand setName(@Nullable String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public Brand setUrl(@Nullable String url) {
        this.url = url;
        return this;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public Brand setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return id.equals(brand.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
