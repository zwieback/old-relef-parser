package io.github.zwieback.relef.importers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class Importer<T> {

    private final String fileName;

    @Value("${import.path}")
    private String importPath;

    protected Importer(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;
    }

    public abstract List<T> doImport();

    @NotNull
    protected String buildFileName(@NotNull String fileName) {
        return importPath + File.separator + fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
