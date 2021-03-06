package io.github.zwieback.relef.parsers;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Service
public class FileParser {

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
    @SneakyThrows(IOException.class)
    public Document parseInputStream(InputStream inputStream) {
        return Jsoup.parse(inputStream, defaultCharset.name(), domainUrl);
    }
}
