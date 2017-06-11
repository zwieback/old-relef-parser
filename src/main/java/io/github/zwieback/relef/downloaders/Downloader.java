package io.github.zwieback.relef.downloaders;

import io.github.zwieback.relef.exceptions.ExceededErrorsCountException;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Downloader<T> {

    private static final Logger log = LogManager.getLogger(Downloader.class);
    private static final int ENTITY_ON_PAGE_COUNT = 100;

    private final RestService restService;
    private final FileService fileService;

    @Value("${max.errors.number:10}")
    private int maxErrorsNumber;

    @Value("${download.path}")
    private String downloadPath;

    @Value("${download.skip.downloaded}")
    private boolean skipDownloaded;

    @Autowired
    public Downloader(RestService restService, FileService fileService) {
        this.restService = restService;
        this.fileService = fileService;
    }

    /**
     * Download all entities page by page.
     */
    public void download() {
        Page<T> page = findAll(buildPageRequest());
        log.info(String.format("Total elements %d on total pages %d", page.getTotalElements(), page.getTotalPages()));
        downloadAllPages(page);
    }

    private void downloadAllPages(Page<T> page) {
        while (!page.isLast()) {
            downloadPage(page);
            Pageable pageable = page.nextPageable();
            page = findAll(pageable);
        }
        downloadPage(page);
    }

    private void downloadPage(Page<T> page) {
        log.info(String.format("Download page %d of %d with %d entities", page.getNumber() + 1, page.getTotalPages(),
                page.getNumberOfElements()));
        downloadEntities(page.getContent());
    }

    private void downloadEntities(List<T> entities) {
        int entitySize = entities.size();
        AtomicInteger i = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();
        entities.forEach(entity -> {
            String url = getEntityUrl(entity);
            log.debug(String.format("Download entity %d of %d (%s)", i.incrementAndGet(), entitySize, url));
            try {
                downloadEntity(entity);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                if (errorCount.incrementAndGet() > maxErrorsNumber) {
                    throw new ExceededErrorsCountException();
                }
            }
        });
    }

    boolean downloadEntity(T entity) {
        String url = getEntityUrl(entity);
        String fileName = getFileName(entity);
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(fileName)) {
            return false;
        }
        String catalogPath = getEntityCatalog(entity);
        String fileNameToSave = buildFileName(catalogPath, fileName);
        if (skipDownloaded && fileService.exists(fileNameToSave)) {
            return true;
        }
        byte[] bytes = downloadContentAsBytes(url);
        if (bytes == null) {
            return false;
        }
        fileService.writeBytes(bytes, fileNameToSave);
        return true;
    }

    private byte[] downloadContentAsBytes(@NotNull String url) {
        HttpHeaders httpHeaders = restService.buildEmptyHeaders();
        HttpEntity<?> httpEntity = restService.buildEntity(null, httpHeaders);
        return restService.get(url, httpEntity, byte[].class);
    }

    abstract Page<T> findAll(Pageable pageable);

    @Nullable
    abstract String getEntityUrl(T entity);

    @Nullable
    abstract String getFileName(T entity);

    @NotNull
    abstract String getEntityCatalog(T entity);

    @NotNull
    private String buildFileName(@NotNull String catalogPath, @NotNull String relativeFileName) {
        return Paths.get(downloadPath, catalogPath, relativeFileName).toString();
    }

    @NotNull
    private static Pageable buildPageRequest() {
        return new PageRequest(0, ENTITY_ON_PAGE_COUNT);
    }
}
