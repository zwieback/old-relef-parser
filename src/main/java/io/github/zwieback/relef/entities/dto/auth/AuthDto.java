package io.github.zwieback.relef.entities.dto.auth;

import lombok.Data;

@Data
public class AuthDto {

    private String action;

    private String sid;

    private String userId;

    private MarkDto mark;
}
