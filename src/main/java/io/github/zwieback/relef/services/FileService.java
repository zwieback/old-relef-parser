package io.github.zwieback.relef.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final Logger log = LogManager.getLogger(FileService.class);

    private final Charset defaultCharset;

    @Autowired
    public FileService(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public void writeDocument(Document document, String fileName) {
        try {
            Files.write(Paths.get(fileName), document.toString().getBytes(defaultCharset));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }
}
