package io.github.zwieback.relef.downloaders.exporters;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.quote.AlwaysQuoteMode;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

@Service
public class NameExporter {

    private final Charset defaultCharset;

    @Value("${download.path}")
    private String downloadPath;

    @Value("${download.csv.names}")
    private String downloadCsvNames;

    @Value("${download.csv.delimiter}")
    private Character downloadCsvDelimiter;

    public NameExporter(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    @SneakyThrows(IOException.class)
    public void export(Map<String, String> names) {
        @Cleanup ICsvListWriter writer = new CsvListWriter(buildWriter(), buildCsvPreference());
        CellProcessor[] processors = buildProcessors();
        for (Map.Entry<String, String> nameEntry : names.entrySet()) {
            String processedName = nameEntry.getKey();
            String nativeName = nameEntry.getValue();
            writer.write(Arrays.asList(processedName, nativeName), processors);
        }
    }

    @SneakyThrows(IOException.class)
    private Writer buildWriter() {
        return Files.newBufferedWriter(Paths.get(buildFileName()), defaultCharset);
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
