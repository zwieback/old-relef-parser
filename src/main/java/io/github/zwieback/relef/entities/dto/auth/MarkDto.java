package io.github.zwieback.relef.entities.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarkDto {

    private String sid;

    private Boolean buy;

    private Boolean auth;

    private Boolean loyalty;

    private Boolean bookmark;

    @JsonProperty("banner_top")
    private Boolean bannerTop;
}
