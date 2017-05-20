package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.zwieback.relef.utils.json.ProductPricesDeserializer;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public ProductPricesDto() {
    }

    public Map<Long, ProductDto> getProductMap() {
        return productMap;
    }

    public ProductPricesDto setProductMap(Map<Long, ProductDto> productMap) {
        this.productMap = productMap;
        return this;
    }

    public String getAction() {
        return action;
    }

    public ProductPricesDto setAction(String action) {
        this.action = action;
        return this;
    }

    public Boolean getCanBuy() {
        return canBuy;
    }

    public ProductPricesDto setCanBuy(Boolean canBuy) {
        this.canBuy = canBuy;
        return this;
    }

    public Integer getCompare() {
        return compare;
    }

    public ProductPricesDto setCompare(Integer compare) {
        this.compare = compare;
        return this;
    }

    public Boolean getSoapm() {
        return soapm;
    }

    public ProductPricesDto setSoapm(Boolean soapm) {
        this.soapm = soapm;
        return this;
    }

    @Nullable
    public ErrorDto getError() {
        return error;
    }

    public ProductPricesDto setError(@Nullable ErrorDto error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "ProductPricesDto{" +
                "productMap=" + productMap +
                ", action='" + action + '\'' +
                ", canBuy=" + canBuy +
                ", compare=" + compare +
                ", soapm=" + soapm +
                ", error=" + error +
                '}';
    }
}
