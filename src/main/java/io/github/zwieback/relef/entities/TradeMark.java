package io.github.zwieback.relef.entities;

import org.jetbrains.annotations.Nullable;

public class TradeMark {

    @Nullable
    private String name;

    @Nullable
    private String url;

    public TradeMark() {
    }

    public TradeMark(@Nullable String name, @Nullable String url) {
        this.name = name;
        this.url = url;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TradeMark{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
