package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.zwieback.relef.utils.json.ProductPricesDeserializer;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonDeserialize(using = ProductPricesDeserializer.class)
public class ProductPricesDto {

    @JsonProperty("product")
    @JsonDeserialize(as = LinkedHashMap.class, keyAs = Long.class, contentAs = ProductDto.class)
    private Map<Long, ProductDto> productMap;

    private String action;

    @JsonProperty("canbuy")
    private Boolean canBuy;

    private Integer compare;

    private Boolean soapm;

    @Nullable
    private ErrorDto error;
}
