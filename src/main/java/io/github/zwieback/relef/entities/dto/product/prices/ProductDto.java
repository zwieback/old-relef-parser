package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private BigDecimal price;

    private String amount;

    private Integer available;

    @JsonProperty("oldprice")
    private BigDecimal oldPrice;

    @JsonProperty("blfriday")
    private Boolean blackFriday;
}
