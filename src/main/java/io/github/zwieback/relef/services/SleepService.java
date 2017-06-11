package io.github.zwieback.relef.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SleepService {

    @Value("${request.timeout:1000}")
    private long requestTimeout;

    /**
     * Sleep only if responseExecutionTime lower than requestTimeout.
     *
     * @param responseExecutionTime how many milliseconds response was executed
     */
    @SneakyThrows(InterruptedException.class)
    public void sleepIfNeeded(long responseExecutionTime) {
        if (responseExecutionTime >= requestTimeout) {
            return;
        }
        Thread.sleep(requestTimeout - responseExecutionTime);
    }
}
