package io.github.zwieback.relef.services;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Charset defaultCharset;

    @Autowired
    public FileService(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public void writeDocument(Document document, String fileName) {
        writeBytes(document.toString().getBytes(defaultCharset), fileName);
    }

    @SneakyThrows(IOException.class)
    public void writeBytes(byte[] bytes, String fileName) {
        Path parentDir = Paths.get(fileName).getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        Files.write(Paths.get(fileName), bytes);
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
