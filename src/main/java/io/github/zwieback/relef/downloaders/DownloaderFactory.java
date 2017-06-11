package io.github.zwieback.relef.downloaders;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_DOWNLOAD_PRODUCT_IMAGE;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE;

@Service
public class DownloaderFactory {

    private final ProductImageDownloader productImageDownloader;
    private final SamsonProductImageDownloader samsonProductImageDownloader;

    @Autowired
    public DownloaderFactory(ProductImageDownloader productImageDownloader,
                             SamsonProductImageDownloader samsonProductImageDownloader) {
        this.productImageDownloader = productImageDownloader;
        this.samsonProductImageDownloader = samsonProductImageDownloader;
    }

    public List<Downloader> determineDownloadersByCommandLine(CommandLine cmd) {
        List<Downloader> downloaders = new ArrayList<>();
        if (cmd.hasOption(OPTION_DOWNLOAD_PRODUCT_IMAGE)) {
            downloaders.add(productImageDownloader);
        }
        if (cmd.hasOption(OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE)) {
            samsonProductImageDownloader.setImportFileName(cmd.getOptionValue(OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE));
            downloaders.add(samsonProductImageDownloader);
        }
        if (downloaders.isEmpty()) {
            throw new IllegalArgumentException("No downloader found");
        }
        return downloaders;
    }
}
