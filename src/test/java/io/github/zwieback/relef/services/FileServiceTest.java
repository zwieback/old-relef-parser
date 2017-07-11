package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class FileServiceTest {

    private static final String INCORRECT_FILE_NAME = "/:\\";

    @SuppressWarnings("unused")
    @Autowired
    private FileService fileService;

    private Path tempDir;

    @After
    public void cleanUp() {
        if (tempDir != null) {
            recursiveDeleteFilesInDirectory(tempDir.toFile());
        }
    }

    @SneakyThrows(IOException.class)
    @Test
    public void test_writeDocument_should_write_document_to_temp_file() {
        tempDir = Files.createTempDirectory("temp_files");
        String tempFile = tempDir.toFile().getAbsolutePath() + File.separator + ".tmp";
        fileService.writeDocument(buildEmptyDocument(), tempFile);
        assertThat(Paths.get(tempFile)).exists();
    }

    @Test(expected = IOException.class)
    public void test_writeDocument_should_throws_exception() {
        fileService.writeDocument(buildEmptyDocument(), INCORRECT_FILE_NAME);
    }

    @NotNull
    private static Document buildEmptyDocument() {
        return Document.createShell("");
    }

    @SneakyThrows(IOException.class)
    private static void recursiveDeleteFilesInDirectory(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    recursiveDeleteFilesInDirectory(file);
                }
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete: " + f);
        }
    }
}
