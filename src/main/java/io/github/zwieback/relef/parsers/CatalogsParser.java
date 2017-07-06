package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import io.github.zwieback.relef.services.UrlBuilder;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CatalogsParser {

    private final InternalParser internalParser;
    private final UrlBuilder urlBuilder;
    private final UrlParser urlParser;

    @Autowired
    public CatalogsParser(InternalParser internalParser, UrlBuilder urlBuilder, UrlParser urlParser) {
        this.internalParser = internalParser;
        this.urlBuilder = urlBuilder;
        this.urlParser = urlParser;
    }

    public Document parseUrl(String url) {
        return internalParser.get(url);
    }

    /**
     * Parse and return brands.
     *
     * @param document catalogs document
     * @return brands
     */
    public List<Brand> parseBrands(Document document) {
        Elements brands = document.select("li.gal-item > ul.inner-list > li > a");
        return brands.stream().map(this::parseBrand).collect(toList());
    }

    @NotNull
    private Brand parseBrand(Element brandNode) {
        String url = urlBuilder.buildPathUrl(brandNode.attr("href"));
        Long id = urlParser.parseBrandId(url);
        Element imageNode = brandNode.select("img").first();
        String imageUrl = imageNode == null ? null : urlBuilder.buildQueryUrl(imageNode.attr("data-src"));
        return new Brand(id, url, imageUrl);
    }

    /**
     * Parse and return catalogs of selected level.
     *
     * @param document catalogs document
     * @param level    catalog level
     * @return catalogs of selected level
     */
    public List<Catalog> parseCatalogsOfLevel(Document document, CatalogLevel level) {
        Elements catalogs = document.select(level.getCssQueryForUrl());
        return catalogs.stream()
                .map(catalogNode -> parseCatalog(catalogNode, level))
                .collect(toList());
    }

    private Catalog parseCatalog(Element catalogNode, CatalogLevel level) {
        String url = urlBuilder.buildPathUrl(catalogNode.attr("href"));
        Long id = urlParser.parseCatalogIdFromCatalogUrl(url);
        String name = catalogNode.text();
        return new Catalog()
                .setId(id)
                .setUrl(url)
                .setName(name)
                .setLevel(level);
    }

    /**
     * Parse and return tree of catalogs.
     *
     * @param catalogNode catalog node
     * @param level       catalog level
     * @return tree of catalogs
     */
    public List<Catalog> parseTreeOfCatalogs(Element catalogNode, CatalogLevel level) {
        Elements catalogs = catalogNode.select(level.getCssQueryForNode());
        return catalogs.stream()
                .map(node -> parseCatalogInTree(node, level))
                .collect(toList());
    }

    private Catalog parseCatalogInTree(Element catalogNode, CatalogLevel level) {
        List<Catalog> children = new ArrayList<>();
        if (level.hasNext()) {
            children = parseTreeOfCatalogs(catalogNode, level.next());
        }
        Element urlNode = catalogNode.select(level.getCssQueryForNodeUrl()).first();
        Catalog catalog = parseCatalog(urlNode, level);
        children.forEach(child -> child.setParent(catalog));
        return catalog.setChildren(children);
    }

    /**
     * Parse and return catalog urls of selected level.
     *
     * @param document catalogs document
     * @param level    catalog level
     * @return catalog urls
     */
    public List<String> parseCatalogUrls(Document document, CatalogLevel level) {
        Elements catalogs = document.select(level.getCssQueryForUrl());
        return catalogs.stream()
                .map(catalog -> urlBuilder.buildPathUrl(catalog.attr("href")))
                .collect(toList());
    }
}
