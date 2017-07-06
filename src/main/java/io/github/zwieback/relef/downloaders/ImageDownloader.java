package io.github.zwieback.relef.downloaders;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.TreeMap;

@Service
public abstract class ImageDownloader<T> extends Downloader<T> {

    private final NameProcessor nameProcessor;
    private final NameExporter nameExporter;

    /**
     * row - catalog
     * column - file name
     * value - name
     */
    private final Table<String, String, String> names;

    @Autowired
    ImageDownloader(RestService restService,
                    FileService fileService,
                    NameProcessor nameProcessor,
                    NameExporter nameExporter) {
        super(restService, fileService);
        this.nameProcessor = nameProcessor;
        this.nameExporter = nameExporter;

        this.names = HashBasedTable.create();
    }

    @Override
    public void download() {
        super.download();
        exportNames();
    }

    private void exportNames() {
        names.rowMap().forEach((catalogPath, nameMap) ->
                nameExporter.export(catalogPath, new TreeMap<>(nameMap)));
    }

    @Override
    boolean downloadEntity(T entity) {
        boolean downloaded = super.downloadEntity(entity);
        String entityName = getEntityName(entity);
        if (!StringUtils.isEmpty(entityName)) {
            if (downloaded) {
                names.put(getEntityCatalog(entity),
                        nameProcessor.getProcessedFileName(entityName),
                        nameProcessor.getProcessedName(entityName));
            } else {
                names.remove(getEntityCatalog(entity), nameProcessor.getProcessedFileName(entityName));
            }
        }
        return downloaded;
    }

    @Nullable
    abstract String getEntityName(T entity);
}
