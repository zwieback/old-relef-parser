package io.github.zwieback.relef.importers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Importer<T> {

    @Getter
    @NotNull
    private final String fileName;

    @Value("${import.path}")
    private String importPath;

    public abstract List<T> doImport();

    @NotNull
    protected String buildFileName(@NotNull String fileName) {
        return importPath + File.separator + fileName;
    }
}
