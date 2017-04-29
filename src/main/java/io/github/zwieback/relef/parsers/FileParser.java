package io.github.zwieback.relef.parsers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

@Service
public class FileParser {

    private static final Logger log = LogManager.getLogger(FileParser.class);

    private final Charset defaultCharset;

    @Value("${site.domain}")
    private String domainUrl;

    @Autowired
    public FileParser(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    /**
     * Parse html file to document.
     *
     * @param inputStream input stream from source file
     * @return parsed document
     */
    public Document parseInputStream(InputStream inputStream) {
        try {
            return Jsoup.parse(inputStream, defaultCharset.name(), domainUrl);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }
}
