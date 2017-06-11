package io.github.zwieback.relef.entities.dto.samson;

import org.jetbrains.annotations.NotNull;

public class SamsonProductDto {

    // Наименование
    @NotNull
    private String name;

    // Ссылка на изображение
    @NotNull
    private String photoUrl;

    public SamsonProductDto() {
        name = "";
        photoUrl = "";
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
