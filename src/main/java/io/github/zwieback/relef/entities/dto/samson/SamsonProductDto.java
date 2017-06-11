package io.github.zwieback.relef.entities.dto.samson;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class SamsonProductDto {

    // Наименование
    @NotNull
    private String name = "";

    // Ссылка на изображение
    @NotNull
    private String photoUrl = "";
}
