package io.github.zwieback.relef.downloaders;

import com.google.common.io.Files;
import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.services.TransliterateService;
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

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductImageDownloader extends Downloader<Product> {

    private final StringService stringService;
    private final TransliterateService transliterateService;
    private final ProductRepository productRepository;
    private final NameExporter nameExporter;
    private final UrlBuilder urlBuilder;

    private final Map<String, String> names;

    @Value("${site.domain}")
    private String domainUrl;

    @Autowired
    public ProductImageDownloader(RestService restService,
                                  FileService fileService,
                                  StringService stringService,
                                  TransliterateService transliterateService,
                                  ProductRepository productRepository,
                                  NameExporter nameExporter,
                                  UrlBuilder urlBuilder) {
        super(restService, fileService);
        this.stringService = stringService;
        this.transliterateService = transliterateService;
        this.productRepository = productRepository;
        this.nameExporter = nameExporter;
        this.urlBuilder = urlBuilder;

        this.names = new HashMap<>();
    }

    @Override
    public void download() {
        super.download();
        nameExporter.export(names);
    }

    @Override
    boolean downloadEntity(Product entity) {
        boolean downloaded = super.downloadEntity(entity);
        if (!StringUtils.isEmpty(entity.getName())) {
            if (downloaded) {
                names.put(getProcessedName(entity.getName()), entity.getName());
            } else {
                names.remove(entity.getName());
            }
        }
        return downloaded;
    }

    @Override
    Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
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
        return getProcessedName(entity.getName()) + extension;
    }

    /**
     * Transliterate and replace special characters in source string.
     *
     * @param name source string
     * @return processed string
     */
    @NotNull
    private String getProcessedName(@NotNull String name) {
        String transliterated = transliterateService.transliterate(name);
        return stringService.replaceSpecialCharsByUnderscore(transliterated);
    }
}
