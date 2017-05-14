package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.parsers.exceptions.UrlParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class UrlParser {

    /**
     * Parse catalog id from catalog url.
     * <p>
     * Example:
     * <p>
     * http://relefopt.ru/catalog/69472/
     * returns 69472
     *
     * @param catalogUrl url to catalog
     * @return catalog id if exists
     * @throws UrlParseException if catalog url has incorrect format
     */
    @NotNull
    Long parseCatalogIdFromCatalogUrl(String catalogUrl) {
        return parseIdFromUrl(catalogUrl, 5, 1, "Couldn't parse catalog id from catalog url " + catalogUrl);
    }

    /**
     * Parse catalog id from product url.
     * <p>
     * Example:
     * <p>
     * http://relefopt.ru/catalog/69472/321940415/
     * returns 69472
     *
     * @param productUrl url to product
     * @return catalog id if exists
     * @throws UrlParseException if catalog url has incorrect format
     */
    @NotNull
    public Long parseCatalogIdFromProductUrl(String productUrl) {
        return parseIdFromUrl(productUrl, 6, 2, "Couldn't parse catalog id from product url " + productUrl);
    }

    /**
     * Parse product id from product url.
     * <p>
     * Example:
     * <p>
     * http://relefopt.ru/catalog/69472/321940415/
     * returns 321940415
     *
     * @param productUrl url to product
     * @return catalog id if exists
     * @throws UrlParseException if catalog url has incorrect format
     */
    @NotNull
    public Long parseProductIdFromProductUrl(String productUrl) {
        return parseIdFromUrl(productUrl, 6, 1, "Couldn't parse product id from product url " + productUrl);
    }

    /**
     * Parse brand id from brand url.
     * <p>
     * Example:
     * <p>
     * http://relefopt.ru/brands/48921/
     * returns 48921
     *
     * @param brandUrl url to brand
     * @return brand id if exists
     * @throws UrlParseException if brand url has incorrect format
     */
    @NotNull
    Long parseBrandId(String brandUrl) {
        return parseIdFromUrl(brandUrl, 5, 1, "Couldn't parse id from brand url " + brandUrl);
    }

    @NotNull
    private Long parseIdFromUrl(String url, int partCount, int indexOffset, String exceptionMessage) {
        String[] divided = url.split("/");
        if (divided.length == partCount) {
            int idIndex = divided.length - indexOffset;
            return Long.valueOf(divided[idIndex]);
        }
        throw new UrlParseException(exceptionMessage);
    }
}
