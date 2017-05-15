package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class DateTimeServiceTest {

    @SuppressWarnings("unused")
    @Autowired
    private DateTimeService dateTimeService;

    @Test
    public void test_nowAsMilliseconds_should_be_today() {
        LocalDate expected = LocalDate.now();
        long nowAsMilliseconds = dateTimeService.nowAsMilliseconds();
        LocalDate resultDate = Instant.ofEpochMilli(nowAsMilliseconds).atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(expected, resultDate);
    }

    @Test
    public void test_nowAsLocalDateTime_should_be_today() {
        LocalDate expected = LocalDate.now();
        LocalDateTime nowAsLocalDateTime = dateTimeService.nowAsLocalDateTime();
        LocalDate resultDate = nowAsLocalDateTime.toLocalDate();
        assertEquals(expected, resultDate);
    }
}
