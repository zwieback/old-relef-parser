package io.github.zwieback.relef.services;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Charset defaultCharset;

    @Autowired
    public FileService(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public void writeDocument(Document document, String fileName) throws IOException {
        Files.write(Paths.get(fileName), document.toString().getBytes(defaultCharset));
    }
}
