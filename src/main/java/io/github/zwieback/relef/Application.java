package io.github.zwieback.relef;

import io.github.zwieback.relef.configs.ParserConfig;
import io.github.zwieback.relef.configs.PropertyConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.parsers.CatalogParser;
import io.github.zwieback.relef.parsers.CatalogsParser;
import io.github.zwieback.relef.parsers.FileParser;
import io.github.zwieback.relef.parsers.ProductParser;
import io.github.zwieback.relef.services.FileService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    private final CatalogsParser catalogsParser;
    private final CatalogParser catalogParser;
    private final ProductParser productParser;
    private final FileParser fileParser;
    private final FileService fileService;
    private final ResourceLoader resourceLoader;

    private Application(CatalogsParser catalogsParser, CatalogParser catalogParser, ProductParser productParser,
                        FileParser fileParser, FileService fileService, ResourceLoader resourceLoader) {
        this.catalogsParser = catalogsParser;
        this.catalogParser = catalogParser;
        this.productParser = productParser;
        this.fileParser = fileParser;
        this.fileService = fileService;
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ParserConfig.class);
        context.register(ServiceConfig.class);
        context.register(PropertyConfig.class);
        context.refresh();

        CatalogsParser catalogsParser = context.getBean(CatalogsParser.class);
        CatalogParser catalogParser = context.getBean(CatalogParser.class);
        ProductParser productParser = context.getBean(ProductParser.class);
        FileParser fileParser = context.getBean(FileParser.class);
        FileService fileService = context.getBean(FileService.class);

        Application application = new Application(catalogsParser, catalogParser, productParser, fileParser,
                fileService, context);
        application.parse();

        context.close();
    }

    private void parse() {
        log.info("Parsing started...");
        try {
//            downloadCatalogs();
//            downloadProduct();
//            parseBrands();
//            parseCatalogs();
//            parseCatalog();
//            parseProduct();
        } catch (Exception e) {
            log.error("Parsing completed with error: " + e.getMessage(), e);
            return;
        }
        log.info("Parsing completed successfully");
    }

    private void downloadCatalogs() throws IOException {
        Document catalogsDocument = catalogsParser.parseUrl("http://relefopt.ru/catalog/");
        fileService.writeDocument(catalogsDocument, "./target/catalogs.html");
    }

    private void downloadProduct() throws IOException {
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
    }

    private void parseCatalogs() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalogs.html");
        InputStream catalogs_inputStream = resource.getInputStream();
        Document catalogsDoc = fileParser.parseInputStream(catalogs_inputStream);

        int catalogCount;

        // urls of level
        List<String> catalogUrls = catalogsParser.parseCatalogUrls(catalogsDoc, CatalogLevel.FOURTH);
        catalogCount = catalogUrls.size();
        for (int i = 0; i < 1; i++) {
            log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, catalogUrls.get(i)));
            List<Document> catalogDocs = catalogParser.parseUrl(catalogUrls.get(i));
            for (int j = 0; j < catalogDocs.size(); j++) {
                fileService.writeDocument(catalogDocs.get(j), "./target/catalog_" + (j + 1) + ".html");
            }
        }

        // catalogs of level
        List<Catalog> catalogs = catalogsParser.parseCatalogsOfLevel(catalogsDoc, CatalogLevel.FOURTH);
        catalogCount = catalogs.size();
        for (int i = 0; i < 10; i++) {
            log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, catalogs.get(i)));
        }

        // full tree of catalogs
        List<Catalog> treeOfCatalogs = catalogsParser.parseTreeOfCatalogs(catalogsDoc, CatalogLevel.FIRST);
        catalogCount = treeOfCatalogs.size();
        for (int i = 0; i < 1; i++) {
            log.debug(String.format("Parse catalog %d of %d : %s", i + 1, catalogCount, treeOfCatalogs.get(i)));
        }
    }

    private void parseCatalog() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalog_69472_page_1.html");
        InputStream catalog69472_page1_inputStream = resource.getInputStream();
        Document catalog69472_page1_doc = fileParser.parseInputStream(catalog69472_page1_inputStream);
        List<Product> products = catalogParser.parseProducts(catalog69472_page1_doc, 69472);
        int productCount = products.size();
        for (int i = 0; i < 1; i++) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Parse product %d of %d: %s", i + 1, productCount,
                        products.get(i).toString()));
            } else {
                log.info(String.format("Parse product %d of %d", i + 1, productCount));
            }
        }
    }

    private void parseProduct() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pages/catalog_64323_product_19298702.html");
        InputStream productInputStream = resource.getInputStream();
        Document productDocument = fileParser.parseInputStream(productInputStream);
        Product product = productParser.parseProduct(productDocument, 64323, 19298702);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Parse product %d: %s", product.getId(), product.toString()));
        } else {
            log.info(String.format("Parse product %d", product.getId()));
        }
    }
}
