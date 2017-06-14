package io.github.zwieback.relef.downloaders;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_DOWNLOAD_PRODUCT_IMAGE;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE;

@Service
public class DownloaderFactory {

    private final BeanFactory beanFactory;
    private final ProductImageDownloader productImageDownloader;

    @Autowired
    public DownloaderFactory(BeanFactory beanFactory,
                             ProductImageDownloader productImageDownloader) {
        this.beanFactory = beanFactory;
        this.productImageDownloader = productImageDownloader;
    }

    public List<Downloader> determineDownloadersByCommandLine(CommandLine cmd) {
        List<Downloader> downloaders = new ArrayList<>();
        if (cmd.hasOption(OPTION_DOWNLOAD_PRODUCT_IMAGE)) {
            downloaders.add(productImageDownloader);
        }
        if (cmd.hasOption(OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE)) {
            String importFileName = cmd.getOptionValue(OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE);
            downloaders.add(beanFactory.getBean(SamsonProductImageDownloader.class, importFileName));
        }
        if (downloaders.isEmpty()) {
            throw new IllegalArgumentException("No downloader found");
        }
        return downloaders;
    }
}
