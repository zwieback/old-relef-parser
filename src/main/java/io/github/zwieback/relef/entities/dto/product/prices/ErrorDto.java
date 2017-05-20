package io.github.zwieback.relef.entities.dto.product.prices;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {

    @JsonProperty("product")
    private String message;

    public ErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    public ErrorDto setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
