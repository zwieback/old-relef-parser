package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorDto {

    @JsonProperty("product")
    private String message;
}
