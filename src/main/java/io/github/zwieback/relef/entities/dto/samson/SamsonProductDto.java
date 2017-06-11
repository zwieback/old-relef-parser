package io.github.zwieback.relef.entities.dto.samson;

import org.jetbrains.annotations.NotNull;

public class SamsonProductDto {

    // Наименование
    @NotNull
    private String catalog = "";

    // Наименование
    @NotNull
    private String name = "";

    // Ссылка на изображение
    @NotNull
    private String photoUrl = "";

    @NotNull
    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(@NotNull String catalog) {
        this.catalog = catalog;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(@NotNull String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
