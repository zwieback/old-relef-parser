package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
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
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

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
    public void cleanUp() throws IOException {
        if (tempDir != null) {
            recursiveDeleteFilesInDirectory(tempDir.toFile());
        }
    }

    @Test
    public void test_writeDocument_should_write_document_to_temp_file() throws IOException {
        tempDir = Files.createTempDirectory("temp_files");
        String tempFile = tempDir.toFile().getAbsolutePath() + File.separator + ".tmp";
        fileService.writeDocument(buildEmptyDocument(), tempFile);
        assertTrue(Files.exists(Paths.get(tempFile)));
    }

    @Test(expected = UncheckedIOException.class)
    public void test_writeDocument_should_throws_exception() throws IOException {
        fileService.writeDocument(buildEmptyDocument(), INCORRECT_FILE_NAME);
    }

    @NotNull
    private static Document buildEmptyDocument() {
        return Document.createShell("");
    }

    private static void recursiveDeleteFilesInDirectory(File f) throws IOException {
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
