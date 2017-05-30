package io.github.zwieback.relef.downloaders;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_DOWNLOAD_PRODUCT_IMAGE;

@Service
public class DownloaderFactory {

    private final ProductImageDownloader productImageDownloader;

    @Autowired
    public DownloaderFactory(ProductImageDownloader productImageDownloader) {
        this.productImageDownloader = productImageDownloader;
    }

    public List<Downloader> determineDownloadersByCommandLine(CommandLine cmd) {
        List<Downloader> downloaders = new ArrayList<>();
        if (cmd.hasOption(OPTION_DOWNLOAD_PRODUCT_IMAGE)) {
            downloaders.add(productImageDownloader);
        }
        if (downloaders.isEmpty()) {
            throw new IllegalArgumentException("No downloader found");
        }
        return downloaders;
    }
}
