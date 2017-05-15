package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.format.DateTimeFormatter;

@Component
public abstract class Exporter {

    static final String EXTENSION_XLSX = ".xlsx";

    private final DateTimeService dateTimeService;

    @Value("${export.date.time.pattern}")
    private String dateTimePattern;

    @Value("${export.path}")
    private String exportPath;

    Exporter(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public abstract byte[] toXlsx();

    public abstract String getXlsxFileName();

    String getFileName(String entityName, String extension) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String dateTime = dateTimeService.nowAsLocalDateTime().format(formatter);
        return exportPath + File.separator + entityName + dateTime + extension;
    }
}
