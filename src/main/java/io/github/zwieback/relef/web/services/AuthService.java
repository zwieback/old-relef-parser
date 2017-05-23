package io.github.zwieback.relef.web.services;

import io.github.zwieback.relef.entities.dto.auth.AuthDto;
import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import io.github.zwieback.relef.web.converters.HeadersToMultiValueMapConverter;
import io.github.zwieback.relef.web.rest.services.RestService;
import io.github.zwieback.relef.web.services.exceptions.AuthException;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final RestService restService;
    private final HeadersToMultiValueMapConverter converter;

    @Value("${site.domain.auth}")
    private String authUrl;

    @Value("${site.user.login}")
    private String userLogin;

    @Value("${site.user.password}")
    private String userPassword;

    @Autowired
    public AuthService(RestService restService, HeadersToMultiValueMapConverter converter) {
        this.restService = restService;
        this.converter = converter;
    }

    /**
     * Get sid and authorize.
     *
     * @throws AuthException if authorization is failed
     */
    public void auth() {
        String sid = getSid();
        AuthDto auth = getAuth(sid);
        if (auth == null || StringUtils.isEmpty(auth.getUserId())) {
            throw new AuthException();
        }
    }

    /**
     * Logout.
     */
    public void logout() {
        Headers queryParams = buildLogoutBody();
        postAuthRequest(queryParams);
    }

    @Nullable
    private String getSid() {
        Headers queryParams = buildSidBody();
        AuthDto authDto = postAuthRequest(queryParams);
        return authDto != null && authDto.getMark() != null ? authDto.getMark().getSid() : null;
    }

    private AuthDto getAuth(String sid) {
        Headers queryParams = buildAuthBody(sid, userLogin, userPassword);
        return postAuthRequest(queryParams);
    }

    private AuthDto postAuthRequest(Headers queryParams) {
        MultiValueMap<String, String> body = converter.convert(queryParams);
        HttpHeaders httpHeaders = restService.buildHeaders();
        HttpEntity<?> httpEntity = restService.buildEntity(body, httpHeaders);
        return restService.post(authUrl, httpEntity, AuthDto.class);
    }

    private static Headers buildSidBody() {
        return HeadersBuilder.create().build();
    }

    private static Headers buildAuthBody(String sid, String userLogin, String userPassword) {
        return HeadersBuilder.create()
                .add("sid", sid)
                .add("auth_form", "Y")
                .add("action", "auth")
                .add("user_login", userLogin)
                .add("user_password", userPassword)
                .build();
    }

    private static Headers buildLogoutBody() {
        return HeadersBuilder.create().add("action", "logout").build();
    }
}
