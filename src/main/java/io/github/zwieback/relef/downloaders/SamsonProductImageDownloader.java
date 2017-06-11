package io.github.zwieback.relef.downloaders;

import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.downloaders.samson.SamsonProductDataProvider;
import io.github.zwieback.relef.entities.dto.samson.SamsonProductDto;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SamsonProductImageDownloader extends ImageDownloader<SamsonProductDto> {

    private final SamsonProductImporter productImporter;
    private final NameProcessor nameProcessor;

    private SamsonProductDataProvider dataProvider;
    private String importFileName;

    @Autowired
    public SamsonProductImageDownloader(RestService restService,
                                        FileService fileService,
                                        SamsonProductImporter productImporter,
                                        NameProcessor nameProcessor,
                                        NameExporter nameExporter) {
        super(restService, fileService, nameProcessor, nameExporter);
        this.productImporter = productImporter;
        this.nameProcessor = nameProcessor;
    }

    @Override
    public void download() {
        importProducts();
        super.download();
    }

    private void importProducts() {
        productImporter.setFileName(importFileName);
        List<SamsonProductDto> products = productImporter.doImport();
        dataProvider = new SamsonProductDataProvider(products);
    }

    @Override
    Page<SamsonProductDto> findAll(Pageable pageable) {
        return dataProvider.extractSubList(pageable);
    }

    @Nullable
    @Override
    String getEntityName(SamsonProductDto entity) {
        return entity.getName();
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

    @NotNull
    @Override
    String getEntityCatalog(SamsonProductDto entity) {
        return "samson";
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
