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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class ProductParser {

    private static final Logger log = LogManager.getLogger(ProductParser.class);
    private static final int INFO_PROPERTY_NOT_FOUND_INDEX = -1;
    private static final int MANUFACTURER_INDEX = 0;
    private static final int TRADE_MARK_INDEX = 1;

    private final InternalParser internalParser;
    private final StringService stringService;
    private final UrlBuilder urlBuilder;

    @Autowired
    public ProductParser(InternalParser internalParser, StringService stringService, UrlBuilder urlBuilder) {
        this.internalParser = internalParser;
        this.stringService = stringService;
        this.urlBuilder = urlBuilder;
    }

    /**
     * Add headers from get_product.js and return products document.
     *
     * @param url url to product
     * @return document with products content
     */
    public Document parseUrl(@NotNull String url) {
        Headers headers = HeadersBuilder.create()
                .add("BX-ACTION-TYPE", "get_dynamic")
                .add("BX-REF", "")
                .add("BX-CACHE-MODE", "HTMLCACHE")
                .build();
        return internalParser.get(urlBuilder.buildTrickyProductUrl(url), headers);
    }

    /**
     * Parse available characteristics and properties of product.
     *
     * @param productDoc source document
     * @param catalogId  catalog id
     * @param productId  product id
     * @return product with parsed characteristics and properties
     * @throws HtmlParseException if product not found in source document
     */
    @NotNull
    public Product parseProduct(Document productDoc, @NotNull Long catalogId, @NotNull Long productId) {
        Element productNode = productDoc.select("div.rc-item__item").first();
        if (productNode == null) {
            throw new HtmlParseException("Not found product node in catalog " + catalogId);
        }
        Product product = new Product()
                .setId(productId)
                .setCatalogId(catalogId)
                .setUrl(urlBuilder.buildProductUrl(catalogId, productId))
                .setProperties(parseProductProperties(productId, productNode))
                .setName(parseName(productNode))
                .setDescription(parseDescription(productNode))
                .setPhotoUrl(parsePhotoUrl(productNode))
                .setPhotoCachedUrl(parsePhotoCachedUrl(productNode))
                .setManufacturer(parseManufacturer(productNode))
                .setTradeMark(parseTradeMark(productNode))
                .setParty(parseParty(productNode))
                .setWeight(parseWeight(productNode))
                .setVolume(parseVolume(productNode));
        product
                .setCode(parseCode(product))
                .setArticle(parseArticle(product))
                .setBarcode(parseBarcode(product))
                .setManufacturerCountry(parseManufacturerCountry(product));
        return product;
    }


    @Nullable
    private String parseName(Element productNode) {
        Element url = productNode.select("span.rc-item__prod").first();
        return url == null ? null : url.text();
    }

    @Nullable
    private String parseDescription(Element productNode) {
        Element url = productNode.select("div.detail-block > div > p").first();
        return url == null ? null : url.text();
    }

    @Nullable
    private String parsePhotoUrl(Element productNode) {
        String cssQuery = "div.item-gallery > div.visual > a.highslide";
        Element photoUrl = productNode.select(cssQuery).first();
        return photoUrl == null ? null : urlBuilder.buildQueryUrl(photoUrl.attr("href"));
    }

    @Nullable
    private String parsePhotoCachedUrl(Element productNode) {
        String cssQuery = "div.item-gallery > div.visual > div.img-cont > img";
        Element photoCachedUrl = productNode.select(cssQuery).first();
        return photoCachedUrl == null ? null : urlBuilder.buildQueryUrl(photoCachedUrl.attr("src"));
    }


    @Nullable
    private Manufacturer parseManufacturer(Element productNode) {
        Element manufacturer = parseTitleElement(productNode, MANUFACTURER_INDEX);
        if (manufacturer == null) {
            return null;
        }
        String name = manufacturer.text();
        String url = urlBuilder.buildQueryUrl(manufacturer.attr("href"));
        return new Manufacturer(name, url);
    }

    @Nullable
    private TradeMark parseTradeMark(Element productNode) {
        Element tradeMark = parseTitleElement(productNode, TRADE_MARK_INDEX);
        if (tradeMark == null) {
            return null;
        }
        String name = tradeMark.text();
        String url = urlBuilder.buildQueryUrl(tradeMark.attr("href"));
        return new TradeMark(name, url);
    }

    @Nullable
    private Element parseTitleElement(Element productNode, int elementIndex) {
        Elements titleElements = productNode.select("div.detail-block > strong.title > a");
        return titleElements.size() > elementIndex ? titleElements.get(elementIndex) : null;
    }


    @Nullable
    private String parseParty(Element productNode) {
        return parseInfoProperty(productNode, "Партия / упаковка");
    }

    @Nullable
    private Double parseWeight(Element productNode) {
        String weight = parseInfoProperty(productNode, "Вес, кг");
        return weight == null ? null : Double.valueOf(weight);
    }

    @Nullable
    private Double parseVolume(Element productNode) {
        String volume = parseInfoProperty(productNode, "Объем, м³");
        return volume == null ? null : Double.valueOf(volume);
    }

    @Nullable
    private String parseInfoProperty(Element productNode, String titleName) {
        Element infoTable = productNode.select("table.info-table").first();
        if (infoTable == null) {
            return null;
        }
        int titleIndex = parseInfoTitleIndexByName(infoTable, titleName);
        if (titleIndex != INFO_PROPERTY_NOT_FOUND_INDEX) {
            return parseInfoValueByNum(infoTable, titleIndex);
        }
        return null;
    }

    /**
     * Parse index of products property in info table by title.
     *
     * @param infoTable table contains products party, weight and volume
     * @param titleName title of products property
     * @return index of products property if exists or zero otherwise
     */
    private int parseInfoTitleIndexByName(Element infoTable, String titleName) {
        Elements infoTitles = infoTable.select("th");
        OptionalInt titleIndex = IntStream.range(0, infoTitles.size())
                .filter(titleNum -> titleName.equals(infoTitles.get(titleNum).text()))
                .findAny();
        return titleIndex.orElse(INFO_PROPERTY_NOT_FOUND_INDEX);
    }

    private String parseInfoValueByNum(Element infoTable, int titleIndex) {
        Elements infoValues = infoTable.select("td");
        return infoValues.get(titleIndex).text();
    }


    private List<ProductProperty> parseProductProperties(@NotNull Long productId, Element productNode) {
        Element commonProperties = productNode.select("dl.detail-info").first();
        Elements visibleProperties = productNode.select("ul.simple-list > li.visible");
        Elements hiddenProperties = productNode.select("ul.simple-list > li.hid");
        List<ProductProperty> productProperties = new ArrayList<>();
        productProperties.addAll(parseCommonProductProperties(productId, commonProperties));
        productProperties.addAll(visibleProperties.stream()
                .map(property -> parseProductProperty(productId, property))
                .collect(toList()));
        productProperties.addAll(hiddenProperties.stream()
                .map(property -> parseProductProperty(productId, property))
                .collect(toList()));
        return productProperties;
    }

    private List<ProductProperty> parseCommonProductProperties(@NotNull Long productId, Element commonProperties) {
        String[] properties = commonProperties.html().split("<br>");
        return Arrays.stream(properties)
                .map(property -> parseProductPropertyAsHtml(productId, property))
                .collect(toList());
    }

    @NotNull
    private ProductProperty parseProductProperty(@NotNull Long productId, Element productProperty) {
        return parseProductPropertyAsHtml(productId, productProperty.html());
    }

    @NotNull
    private ProductProperty parseProductPropertyAsHtml(@NotNull Long productId, String productPropertyAsHtml) {
        String text = Jsoup.parse(productPropertyAsHtml).text();
        String name = stringService.clean(text.split("–")[0]);
        String value = stringService.clean(text.split("–")[1]);
        return new ProductProperty(productId, name, value);
    }


    @Nullable
    private String parseCode(Product product) {
        return parseCommonProductProperty(product, "Код");
    }

    @Nullable
    private String parseArticle(Product product) {
        return parseCommonProductProperty(product, "Артикул");
    }

    @Nullable
    private String parseBarcode(Product product) {
        return parseCommonProductProperty(product, "Штрих код");
    }

    @Nullable
    private String parseManufacturerCountry(Product product) {
        return parseCommonProductProperty(product, "Страна производитель");
    }

    @Nullable
    private String parseCommonProductProperty(Product product, String propertyName) {
        Optional<ProductProperty> propertyOptional = product.getProperties().stream()
                .filter(property -> propertyName.equals(property.getName()))
                .findAny();
        return propertyOptional.map(ProductProperty::getValue).orElse(null);
    }
}
