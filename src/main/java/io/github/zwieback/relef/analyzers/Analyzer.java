package io.github.zwieback.relef.analyzers;

import java.util.Objects;

public abstract class Analyzer {

    public abstract void analyze();

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;
    }
}
