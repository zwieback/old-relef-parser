package io.github.zwieback.relef.downloaders;

import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.downloaders.samson.SamsonProductDataProvider;
import io.github.zwieback.relef.entities.dto.samson.SamsonProductDto;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

@Service
public class SamsonProductImageDownloader extends ImageDownloader<SamsonProductDto> {

    private final NameProcessor nameProcessor;
    private final StringService stringService;
    private final SamsonProductImporter productImporter;
    private final String importFileName;

    private SamsonProductDataProvider dataProvider;

    @Autowired
    public SamsonProductImageDownloader(RestService restService,
                                        FileService fileService,
                                        NameProcessor nameProcessor,
                                        NameExporter nameExporter,
                                        StringService stringService,
                                        SamsonProductImporter productImporter,
                                        String importFileName) {
        super(restService, fileService, nameProcessor, nameExporter);
        this.nameProcessor = nameProcessor;
        this.stringService = stringService;
        this.productImporter = productImporter;
        this.importFileName = importFileName;
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
        String normalizedPath = stringService.normalizeWindowsPath(entity.getCatalog());
        return Paths.get("samson", normalizedPath).toString();
    }
}
