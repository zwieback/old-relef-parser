package io.github.zwieback.relef.entities.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkDto {

    private String sid;

    private Boolean buy;

    private Boolean auth;

    private Boolean loyalty;

    private Boolean bookmark;

    @JsonProperty("banner_top")
    private Boolean bannerTop;

    public MarkDto() {
    }

    public String getSid() {
        return sid;
    }

    public MarkDto setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public Boolean getBuy() {
        return buy;
    }

    public MarkDto setBuy(Boolean buy) {
        this.buy = buy;
        return this;
    }

    public Boolean getAuth() {
        return auth;
    }

    public MarkDto setAuth(Boolean auth) {
        this.auth = auth;
        return this;
    }

    public Boolean getLoyalty() {
        return loyalty;
    }

    public MarkDto setLoyalty(Boolean loyalty) {
        this.loyalty = loyalty;
        return this;
    }

    public Boolean getBookmark() {
        return bookmark;
    }

    public MarkDto setBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
        return this;
    }

    public Boolean getBannerTop() {
        return bannerTop;
    }

    public MarkDto setBannerTop(Boolean bannerTop) {
        this.bannerTop = bannerTop;
        return this;
    }

    @Override
    public String toString() {
        return "MarkDto{" +
                "sid='" + sid + '\'' +
                ", buy=" + buy +
                ", auth=" + auth +
                ", loyalty=" + loyalty +
                ", bookmark=" + bookmark +
                ", bannerTop=" + bannerTop +
                '}';
    }
}
