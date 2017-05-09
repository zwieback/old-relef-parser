package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.FileService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Used only for testing and as utility class.
 */
@Component
public class ParseRunner {

    private static final Logger log = LogManager.getLogger(ParseRunner.class);

    private final CatalogsParser catalogsParser;
    private final CatalogParser catalogParser;
    private final ProductParser productParser;
    private final FileParser fileParser;
    private final FileService fileService;
    private final ResourceLoader resourceLoader;
    private final BrandRepository brandRepository;
    private final CatalogRepository catalogRepository;
    private final ProductRepository productRepository;

    @Autowired
    private ParseRunner(CatalogsParser catalogsParser, CatalogParser catalogParser, ProductParser productParser,
                        FileParser fileParser, FileService fileService, ResourceLoader resourceLoader,
                        BrandRepository brandRepository, CatalogRepository catalogRepository,
                        ProductRepository productRepository) {
        this.catalogsParser = catalogsParser;
        this.catalogParser = catalogParser;
        this.productParser = productParser;
        this.fileParser = fileParser;
        this.fileService = fileService;
        this.resourceLoader = resourceLoader;
        this.brandRepository = brandRepository;
        this.catalogRepository = catalogRepository;
        this.productRepository = productRepository;
    }

    public void parse() {
        log.info("Parsing started...");
        try {
//            downloadCatalogs();
            downloadCatalog();
//            downloadProduct();
//            parseBrands();
//            parseCatalogs();
//            parseCatalog();
//            parseProduct();
//            loadCatalog();
        } catch (Exception e) {
            log.error("Parsing completed with error: " + e.getMessage(), e);
            return;
        }
        log.info("Parsing completed successfully");
    }

    private void downloadCatalogs() {
        Document catalogsDocument = catalogsParser.parseUrl("http://relefopt.ru/catalog/");
        fileService.writeDocument(catalogsDocument, "./target/catalogs.html");
    }

    private void downloadCatalog() {
        List<Document> catalogDocuments = catalogParser.parseUrl("http://relefopt.ru/catalog/70126/");
        IntStream.range(0, catalogDocuments.size())
                .forEach(i -> fileService.writeDocument(catalogDocuments.get(i),
                        String.format("./target/catalog_70126_page_%d.html", i + 1)));
    }

    private void downloadProduct() {
        Document productDocument = productParser.parseUrl("http://relefopt.ru/catalog/68711/34259/");
        fileService.writeDocument(productDocument, "./target/catalog_68711_product_34259.html");
    }

    private void parseBrands() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalogs.html");
        InputStream catalogs_inputStream = resource.getInputStream();
        Document catalogsDoc = fileParser.parseInputStream(catalogs_inputStream);

        List<Brand> brands = catalogsParser.parseBrands(catalogsDoc);
        int brandCount = brands.size();
        for (int i = 0; i < 1; i++) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Parse brand %d of %d: %s", i + 1, brandCount, brands.get(i).toString()));
            } else {
                log.info(String.format("Parse brand %d of %d", i + 1, brandCount));
            }
        }
        brandRepository.save(brands);
    }

    private void parseCatalogs() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalogs.html");
        InputStream catalogs_inputStream = resource.getInputStream();
        Document catalogsDoc = fileParser.parseInputStream(catalogs_inputStream);

        int catalogCount;

//        // urls of level
//        List<String> catalogUrls = catalogsParser.parseCatalogUrls(catalogsDoc, CatalogLevel.FOURTH);
//        catalogCount = catalogUrls.size();
//        for (int i = 0; i < 1; i++) {
//            if (log.isDebugEnabled()) {
//                log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, catalogUrls.get(i)));
//            } else {
//                log.info(String.format("Parse catalog %d of %d", i + 1, catalogCount));
//            }
//            List<Document> catalogDocs = catalogParser.parseUrl(catalogUrls.get(i));
//            for (int j = 0; j < catalogDocs.size(); j++) {
//                fileService.writeDocument(catalogDocs.get(j), "./target/catalog_" + (j + 1) + ".html");
//            }
//        }
//
//        // catalogs of level
//        List<Catalog> catalogs = catalogsParser.parseCatalogsOfLevel(catalogsDoc, CatalogLevel.FOURTH);
//        catalogCount = catalogs.size();
//        for (int i = 0; i < 1; i++) {
//            if (log.isDebugEnabled()) {
//                log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, catalogs.get(i)));
//            } else {
//                log.info(String.format("Parse catalog %d of %d", i + 1, catalogCount));
//            }
//        }

        // full tree of catalogs
        List<Catalog> treeOfCatalogs = catalogsParser.parseTreeOfCatalogs(catalogsDoc, CatalogLevel.FIRST);
        catalogCount = treeOfCatalogs.size();
        for (int i = 0; i < 1; i++) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, treeOfCatalogs.get(i)));
            } else {
                log.info(String.format("Parse catalog %d of %d", i + 1, catalogCount));
            }
        }
        saveTreeOfCatalogs(treeOfCatalogs);
    }

    @Transactional
    private void saveTreeOfCatalogs(List<Catalog> catalogs) {
        catalogs.forEach(catalog -> {
            if (!catalog.getChildren().isEmpty()) {
                saveTreeOfCatalogs(catalog.getChildren());
            }
        });
        catalogRepository.save(catalogs);
    }

    private void parseCatalog() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalog_69472_page_1.html");
        InputStream catalog69472_page1_inputStream = resource.getInputStream();
        Document catalog69472_page1_doc = fileParser.parseInputStream(catalog69472_page1_inputStream);

        List<Product> products = catalogParser.parseProducts(catalog69472_page1_doc, 69472L);
        int productCount = products.size();
        for (int i = 0; i < 1; i++) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Parse product %d of %d: %s", i + 1, productCount,
                        products.get(i).toString()));
            } else {
                log.info(String.format("Parse product %d of %d", i + 1, productCount));
            }
        }
        productRepository.save(products);
    }

    private void parseProduct() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalog_64323_product_19298702.html");
        InputStream productInputStream = resource.getInputStream();
        Document productDocument = fileParser.parseInputStream(productInputStream);

        Product product = productParser.parseProduct(productDocument, 64323L, 19298702L);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Parse product %d: %s", product.getId(), product.toString()));
        } else {
            log.info(String.format("Parse product %d", product.getId()));
        }
        productRepository.save(product);
    }

    private void loadCatalog() {
        Catalog catalog = catalogRepository.findOne(69472L);
        if (catalog != null) {
            log.debug(catalog);
            log.debug(catalog.getProducts());
        } else {
            log.warn("Catalog " + 69472L + " not exists!");
        }
    }
}
