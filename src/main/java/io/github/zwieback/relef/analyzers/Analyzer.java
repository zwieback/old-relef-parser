package io.github.zwieback.relef.analyzers;

import java.util.Objects;

public abstract class Analyzer {

    private final String fileName;

    Analyzer(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;
    }

    public abstract void analyze();

    public String getFileName() {
        return fileName;
    }
}
