package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Catalog;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CatalogMerger extends Merger<Catalog, Long> {

    @Override
    public Catalog merge(@NotNull Catalog existedCatalog, @NotNull Catalog parsedCatalog) {
        if (!parsedCatalog.getId().equals(existedCatalog.getId())) {
            return parsedCatalog;
        }
        if (needToMerge(existedCatalog.getUrl(), parsedCatalog.getUrl())) {
            existedCatalog.setUrl(parsedCatalog.getUrl());
        }
        if (needToMerge(existedCatalog.getName(), parsedCatalog.getName())) {
            existedCatalog.setName(parsedCatalog.getName());
        }
        if (needToMerge(existedCatalog.getLevel(), parsedCatalog.getLevel())) {
            existedCatalog.setLevel(parsedCatalog.getLevel());
        }
        if (needToMerge(existedCatalog.getParent(), parsedCatalog.getParent())) {
            existedCatalog.setParent(parsedCatalog.getParent());
        }
        return existedCatalog;
    }

    @NotNull
    @Override
    Long getId(Catalog catalog) {
        return catalog.getId();
    }
}
