package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProductDto {

    private BigDecimal price;

    private String amount;

    private Integer available;

    @JsonProperty("oldprice")
    private BigDecimal oldPrice;

    @JsonProperty("blfriday")
    private Boolean blackFriday;

    public ProductDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public ProductDto setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public Integer getAvailable() {
        return available;
    }

    public ProductDto setAvailable(Integer available) {
        this.available = available;
        return this;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public ProductDto setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
        return this;
    }

    public Boolean getBlackFriday() {
        return blackFriday;
    }

    public ProductDto setBlackFriday(Boolean blackFriday) {
        this.blackFriday = blackFriday;
        return this;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "price=" + price +
                ", amount=" + amount +
                ", available=" + available +
                ", oldPrice=" + oldPrice +
                ", blackFriday=" + blackFriday +
                '}';
    }
}
