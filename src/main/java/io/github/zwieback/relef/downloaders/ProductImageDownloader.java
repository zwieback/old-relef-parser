package io.github.zwieback.relef.downloaders;

import com.google.common.io.Files;
import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.services.UrlBuilder;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;

@Service
public class ProductImageDownloader extends ImageDownloader<Product> {

    private final ProductRepository productRepository;
    private final StringService stringService;
    private final NameProcessor nameProcessor;
    private final UrlBuilder urlBuilder;

    @Value("${site.domain}")
    private String domainUrl;

    @Autowired
    public ProductImageDownloader(RestService restService,
                                  FileService fileService,
                                  ProductRepository productRepository,
                                  StringService stringService,
                                  NameProcessor nameProcessor,
                                  NameExporter nameExporter,
                                  UrlBuilder urlBuilder) {
        super(restService, fileService, nameProcessor, nameExporter);
        this.productRepository = productRepository;
        this.stringService = stringService;
        this.nameProcessor = nameProcessor;
        this.urlBuilder = urlBuilder;
    }

    @Override
    Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Nullable
    @Override
    String getEntityName(Product entity) {
        return entity.getName();
    }

    @Nullable
    @Override
    String getEntityUrl(Product entity) {
        if (entity.getXmlId() != null) {
            return urlBuilder.buildProductPhotoUrl(entity.getXmlId());
        }
        if (StringUtils.isEmpty(entity.getPhotoUrl()) || domainUrl.equals(entity.getPhotoUrl())) {
            return null;
        }
        return entity.getPhotoUrl();
    }

    @Nullable
    @Override
    String getFileName(Product entity) {
        if (StringUtils.isEmpty(entity.getName())) {
            return null;
        }
        String extension = "";
        if (!StringUtils.isEmpty(entity.getPhotoUrl())) {
            extension = Files.getFileExtension(entity.getPhotoUrl());
            extension = StringUtils.isEmpty(extension) ? "" : "." + extension;
        }
        return nameProcessor.getProcessedFileName(entity.getName()) + extension;
    }

    @NotNull
    @Override
    String getEntityCatalog(Product entity) {
        String catalogPath = getCatalogPath(entity.getCatalog());
        String normalizedPath = stringService.normalizeWindowsPath(catalogPath);
        return Paths.get("relef", normalizedPath).toString();
    }

    @NotNull
    private String getCatalogPath(@Nullable Catalog catalog) {
        if (catalog == null) {
            return "";
        }
        return Paths.get(getCatalogPath(catalog.getParent()), catalog.getName()).toString();
    }
}
