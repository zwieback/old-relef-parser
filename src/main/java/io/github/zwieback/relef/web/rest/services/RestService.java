package io.github.zwieback.relef.web.rest.services;

import io.github.zwieback.relef.services.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestService {

    private final RestTemplate restTemplate;
    private final SleepService sleepService;

    @Value("${client.userAgent}")
    private String userAgent;

    @Autowired
    public RestService(RestTemplate restTemplate, SleepService sleepService) {
        this.restTemplate = restTemplate;
        this.sleepService = sleepService;
    }

    public HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = buildEmptyHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public HttpHeaders buildEmptyHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.USER_AGENT, userAgent);
        return httpHeaders;
    }

    public <T> HttpEntity<T> buildEntity(T body, HttpHeaders httpHeaders) {
        return new HttpEntity<>(body, httpHeaders);
    }

    public <T> T get(String url, HttpEntity<?> entity, Class<T> responseType) {
        return exchange(url, HttpMethod.GET, entity, responseType, new HashMap<>());
    }

    public <T> T post(String url, HttpEntity<?> entity, Class<T> responseType) {
        return post(url, entity, responseType, new HashMap<>());
    }

    public <T> T post(String url, HttpEntity<?> entity, Class<T> responseType, Map<String, ?> urlParams) {
        return exchange(url, HttpMethod.POST, entity, responseType, urlParams);
    }

    private <T> T exchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> responseType,
                           Map<String, ?> urlParams) {
        StopWatch watch = new StopWatch();
        watch.start();
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType, urlParams);
        watch.stop();
        sleepService.sleepIfNeeded(watch.getTotalTimeMillis());
        return response.getBody();
    }
}
