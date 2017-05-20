package io.github.zwieback.relef.services;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class HeadersBuilder {

    private final Headers headers;

    private HeadersBuilder() {
        headers = new Headers();
    }

    @NotNull
    public static HeadersBuilder create() {
        return new HeadersBuilder();
    }

    public HeadersBuilder add(String key, String value) {
        headers.put(key, value);
        return this;
    }

    @Contract(pure = true)
    public Headers build() {
        return headers;
    }

    public static class Headers extends LinkedHashMap<String, String> {
        private static final long serialVersionUID = -2926131010422179665L;
    }
}
