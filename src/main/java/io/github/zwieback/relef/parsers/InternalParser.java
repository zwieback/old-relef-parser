package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import io.github.zwieback.relef.services.SleepService;
import lombok.SneakyThrows;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class InternalParser {

    private static final Logger log = LogManager.getLogger(InternalParser.class);
    private static final String GZIP_DEFLATE_ENCODING = "gzip, deflate";

    private final SleepService sleepService;

    @Value("${client.userAgent}")
    private String userAgent;

    public InternalParser(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    @PostConstruct
    public void init() {
        log.info("User agent is " + userAgent);
    }

    /**
     * Do request to url by method with headers.
     * Maximum request count in one second is 2 or we will be banned after 100 requests
     * <a href="https://dev.1c-bitrix.ru/rest_help/rest_sum/index.php">(proof)</a>.
     *
     * @param url     request url
     * @param method  request method
     * @param headers request query headers
     * @return response
     */
    @SneakyThrows(IOException.class)
    private Response parseUrl(String url, Method method, HeadersBuilder.Headers headers) {
        StopWatch watch = new StopWatch();
        watch.start();

        Response response;
        response = Jsoup.connect(url)
                .header(HttpHeaders.ACCEPT_ENCODING, GZIP_DEFLATE_ENCODING)
                .userAgent(userAgent)
//                .cookie("auth", "token")
                // The default maximum is 1048576 bytes (1MB). 0 is treated as an infinite amount
                .maxBodySize(0)
                // The default is 30_000 ms. Time before IOException is thrown. 0 is treated as an infinite timeout
//                .timeout(60_000)
                .data(headers)
                .method(method) // The default is GET
//                .ignoreHttpErrors(true)
                .execute();
        watch.stop();
        sleepService.sleepIfNeeded(watch.getTotalTimeMillis());
        return response;
    }

    Document get(String url) {
        return get(url, buildEmptyParams());
    }

    Document get(String url, Headers headers) {
        Response response = parseUrl(url, Method.GET, headers);
        return parseResponse(response);
    }

    Document post(String url, Headers headers) {
        Response response = parseUrl(url, Method.POST, headers);
        return parseResponse(response);
    }

    @SneakyThrows(IOException.class)
    private static Document parseResponse(Response response) {
        return response.parse();
    }

    @NotNull
    private static HeadersBuilder.Headers buildEmptyParams() {
        return HeadersBuilder.create().build();
    }
}
