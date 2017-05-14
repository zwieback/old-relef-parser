package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.parsers.exceptions.UncheckedHttpStatusException;
import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
public class InternalParser implements InitializingBean {

    private static final Logger log = LogManager.getLogger(InternalParser.class);
    private static final String ACCEPT_ENCODING = "accept-encoding";
    private static final String GZIP_DEFLATE_ENCODING = "gzip, deflate";

    @Value("${request.timeout:500}")
    private long requestTimeout;

    @Value("${client.userAgent}")
    private String userAgent;

    @Override
    public void afterPropertiesSet() throws Exception {
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
    private Response parseUrl(String url, Method method, HeadersBuilder.Headers headers) {
        StopWatch watch = new StopWatch();
        watch.start();

        Response response;
        try {
            response = Jsoup.connect(url)
                    .header(ACCEPT_ENCODING, GZIP_DEFLATE_ENCODING)
                    .userAgent(userAgent)
//                    .cookie("auth", "token")
                    // The default maximum is 1048576 bytes (1MB). 0 is treated as an infinite amount
                    .maxBodySize(0)
                    // The default is 30_000 ms. Time before IOException is thrown. 0 is treated as an infinite timeout
//                    .timeout(60_000)
                    .data(headers)
                    .method(method) // The default is GET
//                    .ignoreHttpErrors(true)
                    .execute();
        } catch (HttpStatusException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedHttpStatusException(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
        watch.stop();
        sleepIfNeeded(watch.getTotalTimeMillis());
        return response;
    }

    /**
     * Sleep only if responseExecutionTime lower than requestTimeout.
     *
     * @param responseExecutionTime how many milliseconds response was executed
     */
    private void sleepIfNeeded(long responseExecutionTime) {
        if (responseExecutionTime >= requestTimeout) {
            return;
        }
        try {
            Thread.sleep(requestTimeout - responseExecutionTime);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    Document get(String url) {
        return get(url, buildEmptyParams());
    }

    Document get(String url, Headers headers) {
        Response response = parseUrl(url, Method.GET, headers);
        try {
            return response.parse();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    Document post(String url, Headers headers) {
        Response response = parseUrl(url, Method.POST, headers);
        try {
            return response.parse();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    @NotNull
    private HeadersBuilder.Headers buildEmptyParams() {
        return HeadersBuilder.create().build();
    }
}
