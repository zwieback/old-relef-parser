package io.github.zwieback.relef.configs;

import io.github.zwieback.relef.downloaders.SamsonProductImageDownloader;
import io.github.zwieback.relef.downloaders.exporters.NameExporter;
import io.github.zwieback.relef.downloaders.processors.NameProcessor;
import io.github.zwieback.relef.importers.excel.SamsonProductImporter;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
@ComponentScan("io.github.zwieback.relef.downloaders")
public class DownloaderConfig {

    private final RestService restService;
    private final FileService fileService;
    private final NameProcessor nameProcessor;
    private final NameExporter nameExporter;
    private final StringService stringService;
    private final Function<String, SamsonProductImporter> samsonProductImporterFactory;

    @Autowired
    public DownloaderConfig(RestService restService,
                            FileService fileService,
                            NameProcessor nameProcessor,
                            NameExporter nameExporter,
                            StringService stringService,
                            Function<String, SamsonProductImporter> samsonProductImporterFactory) {
        this.restService = restService;
        this.fileService = fileService;
        this.nameProcessor = nameProcessor;
        this.nameExporter = nameExporter;
        this.stringService = stringService;
        this.samsonProductImporterFactory = samsonProductImporterFactory;
    }

    @Bean
    public Function<String, SamsonProductImageDownloader> samsonProductImageDownloaderFactory() {
        return this::samsonProductImageDownloader;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    SamsonProductImageDownloader samsonProductImageDownloader(String importFileName) {
        SamsonProductImporter samsonProductImporter = samsonProductImporterFactory.apply(importFileName);
        return new SamsonProductImageDownloader(restService, fileService, nameProcessor, nameExporter, stringService,
                samsonProductImporter);
    }
}
