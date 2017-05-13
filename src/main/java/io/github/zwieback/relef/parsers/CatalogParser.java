package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Manufacturer;
import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.ProductProperty;
import io.github.zwieback.relef.entities.TradeMark;
import io.github.zwieback.relef.parsers.exceptions.HtmlParseException;
import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import io.github.zwieback.relef.services.StringService;
import io.github.zwieback.relef.services.UrlBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class CatalogParser {

    private static final Logger log = LogManager.getLogger(CatalogParser.class);
    private static final int UNKNOWN_PAGES = 0;
    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private static final int ARTICLE_VALUE_INDEX = 1;
    private static final int PARTY_VALUE_INDEX = 1;
    private static final int PROPERTY_NAME_INDEX = 0;
    private static final int PROPERTY_VALUE_INDEX = 1;

    private final InternalParser internalParser;
    private final StringService stringService;
    private final UrlBuilder urlBuilder;

    @Value("${site.productsOnPage:48}")
    private int productsOnPage;

    @Autowired
    public CatalogParser(InternalParser internalParser, StringService stringService, UrlBuilder urlBuilder) {
        this.internalParser = internalParser;
        this.stringService = stringService;
        this.urlBuilder = urlBuilder;
    }

    public List<Document> parseUrl(String url) {
        List<Document> catalogDocuments = new ArrayList<>();
        Document firstCatalogDocument = post(url, FIRST_PAGE, UNKNOWN_PAGES);
        catalogDocuments.add(firstCatalogDocument);

        int totalPages = parseTotalPages(firstCatalogDocument);
        log.debug("Total pages = " + totalPages);
        List<Document> otherCatalogDocuments = IntStream.range(SECOND_PAGE, totalPages + 1)
                .mapToObj(pageNumber -> post(url, pageNumber, totalPages))
                .collect(toList());

        catalogDocuments.addAll(otherCatalogDocuments);
        return catalogDocuments;
    }

    private Document post(String url, int pageNumber, int totalPages) {
        log.debug(String.format("Parse page %d of %s", pageNumber, totalPages == UNKNOWN_PAGES ? "?" : totalPages));
        Headers headers = HeadersBuilder.create()
                .add("AJAX", "Y")
                .add("PAGEN_1", String.valueOf(pageNumber))
                .add("pagenum", String.valueOf(productsOnPage))
                .add("sort_field", "RZN")
                .add("sort_order", "asc")
                .add("in_stock_n", "Y")
                .add("composite", "N")
                .build();
        return internalParser.post(url, headers);
    }

    private int parseTotalPages(Document document) {
        Element totalProductsElement = document.select("div.page-control > p > strong.num").first();
        if (totalProductsElement == null) {
            return 1;
        }
        int totalProducts = Integer.valueOf(totalProductsElement.text());
        if (totalProducts % productsOnPage == 0) {
            return totalProducts / productsOnPage;
        }
        return (totalProducts / productsOnPage) + 1;
    }

    /**
     * Parse product urls from document.
     *
     * @param document source document with product nodes.
     * @return product urls
     */
    public List<String> parseProductUrls(Document document) {
        Elements items = document.select("ul.rc-catalog > li.rc-catalog__item:not(.empty)");
        return items.stream().map(this::parseUrl).collect(toList());
    }

    /**
     * Parse products from document.
     *
     * @param document source document with product nodes
     * @param catalogId catalog id
     * @return products
     */
    public List<Product> parseProducts(Document document, @NotNull Long catalogId) {
        Elements items = document.select("ul.rc-catalog > li.rc-catalog__item");
        return items.stream()
                .map(productNode -> parseProduct(catalogId, productNode))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    /**
     * Parse available characteristics and properties of product.
     *
     * @param catalogId catalog id
     * @param productNode source node
     * @return product with parsed characteristics and properties
     */
    @Nullable
    private Product parseProduct(@NotNull Long catalogId, Element productNode) {
        Long productId = parseId(productNode);
        if (productId == null) {
            return null;
        }
        Product product = new Product()
                .setId(productId)
                .setCatalogId(catalogId)
                .setCode(parseCode(productNode))
                .setArticle(parseArticle(productNode))
                .setName(parseName(productNode))
                .setUrl(urlBuilder.buildProductUrl(catalogId, productId))
                .setPhotoUrl(parsePhotoUrl(productNode))
                .setPhotoCachedUrl(parsePhotoCachedUrl(productNode))
                .setManufacturer(parseManufacturer(productNode))
                .setTradeMark(parseTradeMark(productNode))
                .setParty(parseParty(productNode))
                .setProperties(parseProductProperties(productId, productNode));
        product
                .setManufacturerCountry(parseManufacturerCountry(product));
        return product;
    }

    @Nullable
    private Long parseId(Element productNode) {
        if (productNode.hasClass("empty")) {
            return null;
        }
        String cssQuery = "p.rc-catalog__identify > label.rc-catalog__code > label.rc-catalog__favorite";
        Element idNode = productNode.select(cssQuery).first();
        if (idNode == null) {
            throw new HtmlParseException("Couldn't parse product id");
        }
        return Long.valueOf(idNode.attr("data-id"));
    }

    @Nullable
    private String parseCode(Element productNode) {
        String cssQuery = "p.rc-catalog__identify > label.rc-catalog__code > span.rc-catalog__code-value";
        Element code = productNode.select(cssQuery).first();
        return code == null ? null : code.text();
    }

    @Nullable
    private String parseArticle(Element productNode) {
        Element article = productNode.select("p.rc-catalog__identify > span.rc-catalog__article").first();
        if (article == null) {
            return null;
        }
        String text = Jsoup.parse(article.html()).text();
        String[] articleTitleAndValue = text.split(":");
        if (articleTitleAndValue.length > ARTICLE_VALUE_INDEX) {
            return articleTitleAndValue[ARTICLE_VALUE_INDEX].trim();
        }
        return null;
    }

//    @Nullable
//    private String parseManufacturerCountry(Element productNode) {
//        String propertiesCssQuery = "div.rc-catalog__information > p.rc-catalog__properties > " +
//                "span.rc-catalog__property";
//        Elements properties = productNode.select(propertiesCssQuery);
//        Optional<ProductProperty> propertyOptional = properties.stream()
//                .map(this::parseProductProperty)
//                .filter(property -> "Страна производитель".equals(property.getName()))
//                .findAny();
//        return propertyOptional.map(ProductProperty::getValue).orElse(null);
//    }

    @Nullable
    private String parseManufacturerCountry(Product product) {
        Optional<ProductProperty> propertyOptional = product.getProperties().stream()
                .filter(property -> "Страна производитель".equals(property.getName()))
                .findAny();
        return propertyOptional.map(ProductProperty::getValue).orElse(null);
    }

    @Nullable
    private String parseName(Element productNode) {
        String cssQuery = "div.rc-catalog__description > p.rc-catalog__name > a.rc-catalog__prod";
        Element url = productNode.select(cssQuery).first();
        return url == null ? null : url.text();
    }

    @Nullable
    private String parseUrl(Element productNode) {
        String cssQuery = "div.rc-catalog__description > p.rc-catalog__name > a.rc-catalog__prod";
        Element url = productNode.select(cssQuery).first();
        return url == null ? null : urlBuilder.buildPathUrl(url.attr("href"));
    }

    @Nullable
    private String parsePhotoUrl(Element productNode) {
        String cssQuery = "div.rc-catalog__description > a.rc-catalog__photo";
        Element photoUrl = productNode.select(cssQuery).first();
        return photoUrl == null ? null : urlBuilder.buildQueryUrl(photoUrl.attr("href"));
    }

    @Nullable
    private String parsePhotoCachedUrl(Element productNode) {
        String cssQuery = "div.rc-catalog__description > a.rc-catalog__photo > img.rc-catalog__img";
        Element photoCachedUrl = productNode.select(cssQuery).first();
        return photoCachedUrl == null ? null : urlBuilder.buildQueryUrl(photoCachedUrl.attr("data-src"));
    }

    @Nullable
    private Manufacturer parseManufacturer(Element productNode) {
        String cssQuery = "div.rc-catalog__information > div.rc-catalog__base-properties > p.rc-catalog__properties " +
                " > span.rc-catalog__manufacturer > a.rc-catalog__link";
        Element manufacturer = productNode.select(cssQuery).first();
        if (manufacturer == null) {
            return null;
        }
        String name = manufacturer.text();
        String url = urlBuilder.buildQueryUrl(manufacturer.attr("href"));
        return new Manufacturer(name, url);
    }

    @Nullable
    private TradeMark parseTradeMark(Element productNode) {
        String cssQuery = "div.rc-catalog__information > div.rc-catalog__base-properties > p.rc-catalog__properties " +
                " > span.rc-catalog__tm > a.rc-catalog__link";
        Element tradeMark = productNode.select(cssQuery).first();
        if (tradeMark == null) {
            return null;
        }
        String name = tradeMark.text();
        String url = urlBuilder.buildQueryUrl(tradeMark.attr("href"));
        return new TradeMark(name, url);
    }

    @Nullable
    private String parseParty(Element productNode) {
        Element party = productNode.select("p.rc-catalog__party").first();
        if (party == null) {
            return null;
        }
        String text = Jsoup.parse(party.html()).text();
        String[] partyTitleAndValue = text.split(":");
        if (partyTitleAndValue.length > PARTY_VALUE_INDEX) {
            return partyTitleAndValue[PARTY_VALUE_INDEX].trim();
        }
        return null;
    }

    private List<ProductProperty> parseProductProperties(@NotNull Long productId, Element productNode) {
        String propertiesCssQuery = "div.rc-catalog__information > p.rc-catalog__properties > " +
                "span.rc-catalog__property";
        String hiddenPropertiesCssQuery = "div.rc-catalog__information > p.rc-catalog__properties > " +
                "span.rc-hidden-area > span.rc-catalog__property";
        Elements properties = productNode.select(propertiesCssQuery);
        Elements hiddenProperties = productNode.select(hiddenPropertiesCssQuery);
        List<ProductProperty> productProperties = new ArrayList<>();
        productProperties.addAll(properties.stream()
                .map(property -> parseProductProperty(productId, property))
                .filter(Objects::nonNull)
                .collect(toList()));
        productProperties.addAll(hiddenProperties.stream()
                .map(property -> parseProductProperty(productId, property))
                .filter(Objects::nonNull)
                .collect(toList()));
        return productProperties;
    }

    @Nullable
    private ProductProperty parseProductProperty(@NotNull Long productId, Element productProperty) {
        String text = Jsoup.parse(productProperty.html()).text();
        String[] propertyNameAndValue = text.split("–");
        if (propertyNameAndValue.length > PROPERTY_VALUE_INDEX) {
            String name = stringService.clean(propertyNameAndValue[PROPERTY_NAME_INDEX]);
            String value = stringService.clean(propertyNameAndValue[PROPERTY_VALUE_INDEX]);
            return new ProductProperty(productId, name, value);
        }
        return null;
    }
}
