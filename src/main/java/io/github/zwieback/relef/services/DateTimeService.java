package io.github.zwieback.relef.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DateTimeService {

    public long nowAsMilliseconds() {
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public LocalDateTime nowAsLocalDateTime() {
        return LocalDateTime.now();
    }
}
