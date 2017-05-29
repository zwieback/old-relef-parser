package io.github.zwieback.relef.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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
        writeBytes(document.toString().getBytes(defaultCharset), fileName);
    }

    public void writeBytes(byte[] bytes, String fileName) {
        try {
            Path parentDir = Paths.get(fileName).getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            Files.write(Paths.get(fileName), bytes);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    /**
     * Does the file exist?
     *
     * @param fileName name of source file
     * @return {@code true} if the file exists, {@code false} otherwise
     */
    public boolean exists(@NotNull String fileName) {
        return Files.exists(Paths.get(fileName));
    }
}
