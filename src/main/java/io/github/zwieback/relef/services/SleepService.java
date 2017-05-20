package io.github.zwieback.relef.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SleepService {

    private static final Logger log = LogManager.getLogger(SleepService.class);

    @Value("${request.timeout:1000}")
    private long requestTimeout;

    /**
     * Sleep only if responseExecutionTime lower than requestTimeout.
     *
     * @param responseExecutionTime how many milliseconds response was executed
     */
    public void sleepIfNeeded(long responseExecutionTime) {
        if (responseExecutionTime >= requestTimeout) {
            return;
        }
        try {
            Thread.sleep(requestTimeout - responseExecutionTime);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
