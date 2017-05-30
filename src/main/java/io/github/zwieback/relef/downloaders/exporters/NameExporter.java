package io.github.zwieback.relef.downloaders.exporters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class NameExporter {

    private static final Logger log = LogManager.getLogger(NameExporter.class);

    @Value("${download.path}")
    private String downloadPath;

    @Value("${download.csv.names}")
    private String downloadCsvNames;

    @Value("${download.csv.delimiter}")
    private Character downloadCsvDelimiter;

    public void export(Map<String, String> names) {
        try (ICsvListWriter writer = new CsvListWriter(new FileWriter(buildFileName()), buildCsvPreference())) {
            CellProcessor[] processors = buildProcessors();
            for (Map.Entry<String, String> nameEntry : names.entrySet()) {
                String processedName = nameEntry.getKey();
                String nativeName = nameEntry.getValue();
                writer.write(Arrays.asList(processedName, nativeName), processors);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    private String buildFileName() {
        return downloadPath + File.separator + downloadCsvNames;
    }

    private CsvPreference buildCsvPreference() {
        return new CsvPreference.Builder('"', determineDelimiterChar(), System.lineSeparator())
                .useQuoteMode(new AlwaysQuoteMode())
                .build();
    }

    private int determineDelimiterChar() {
        return (int) downloadCsvDelimiter;
    }

    private static CellProcessor[] buildProcessors() {
        return new CellProcessor[]{
                new NotNull(),
                new NotNull()
        };
    }
}
