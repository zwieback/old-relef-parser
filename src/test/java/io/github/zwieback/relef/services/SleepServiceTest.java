package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.PropertyConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class,
        PropertyConfig.class
})
public class SleepServiceTest {

    private static final long RESPONSE_EXECUTION_TIME_SMALL = 1L;
    private static final long RESPONSE_EXECUTION_TIME_BIG = 50L;

    @SuppressWarnings("unused")
    @Autowired
    private SleepService sleepService;

    @Value("${request.timeout:10}")
    private long requestTimeout;

    @Test
    public void test_sleepIfNeeded_should_sleep_for_requestTimeout() {
        StopWatch watch = new StopWatch();
        watch.start();
        sleepService.sleepIfNeeded(RESPONSE_EXECUTION_TIME_SMALL);
        watch.stop();
        assertThat(watch.getTotalTimeMillis()).isGreaterThanOrEqualTo(requestTimeout - RESPONSE_EXECUTION_TIME_SMALL);
    }

    @Test
    public void test_sleepIfNeeded_should_not_sleep_for_time_bigger_than_requestTimeout() {
        StopWatch watch = new StopWatch();
        watch.start();
        sleepService.sleepIfNeeded(RESPONSE_EXECUTION_TIME_BIG);
        watch.stop();
        assertThat(watch.getTotalTimeMillis()).isGreaterThanOrEqualTo(0L).isLessThanOrEqualTo(10L);
    }
}
