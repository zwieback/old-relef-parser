package io.github.zwieback.relef.downloaders;

import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.downloaders.samson.SamsonProductDataProvider;
import io.github.zwieback.relef.entities.dto.samson.SamsonProductDto;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SamsonProductImageDownloader extends Downloader<SamsonProductDto> {

    private final SamsonProductImporter productImporter;
    private final NameProcessor nameProcessor;
    private final NameExporter nameExporter;

    private final Map<String, String> names;

    private SamsonProductDataProvider dataProvider;
    private String importFileName;

    @Autowired
    public SamsonProductImageDownloader(RestService restService,
                                        FileService fileService,
                                        SamsonProductImporter productImporter,
                                        NameProcessor nameProcessor,
                                        NameExporter nameExporter) {
        super(restService, fileService);
        this.productImporter = productImporter;
        this.nameProcessor = nameProcessor;
        this.nameExporter = nameExporter;

        this.names = new HashMap<>();
    }

    @Override
    public void download() {
        importProducts();
        super.download();
        nameExporter.export(names);
    }

    private void importProducts() {
        productImporter.setFileName(importFileName);
        List<SamsonProductDto> products = productImporter.doImport();
        dataProvider = new SamsonProductDataProvider(products);
    }

    @Override
    boolean downloadEntity(SamsonProductDto entity) {
        boolean downloaded = super.downloadEntity(entity);
        if (!StringUtils.isEmpty(entity.getName())) {
            if (downloaded) {
                names.put(nameProcessor.getProcessedFileName(entity.getName()),
                        nameProcessor.getProcessedName(entity.getName()));
            } else {
                names.remove(entity.getName());
            }
        }
        return downloaded;
    }

    @Override
    Page<SamsonProductDto> findAll(Pageable pageable) {
        return dataProvider.extractSubList(pageable);
    }

    @Nullable
    @Override
    String getEntityUrl(SamsonProductDto entity) {
        return entity.getPhotoUrl();
    }

    @Nullable
    @Override
    String getFileName(SamsonProductDto entity) {
        return nameProcessor.getProcessedFileName(entity.getName());
    }

    /**
     * For SamsonProductImporter only.
     *
     * @param importFileName source file name for product imports
     */
    void setImportFileName(String importFileName) {
        this.importFileName = importFileName;
    }
}
