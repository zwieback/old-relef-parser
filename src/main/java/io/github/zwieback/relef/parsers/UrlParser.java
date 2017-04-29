package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.parsers.exceptions.UrlParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
class UrlParser {

//    /**
//     * Parse catalog id from product url.
//     * <p>
//     * Example:
//     * <p>
//     * http://relefopt.ru/catalog/69472/321940415/
//     * returns 69472
//     *
//     * @param productUrl url to product
//     * @return catalog id if exists
//     * @throws UrlParseException if product url has incorrect format
//     */
//    @NotNull
//    Integer parseCatalogId(String productUrl) {
//        String[] divided = productUrl.split("/");
//        int partCount = 6;
//        if (divided.length == partCount) {
//            // penultimate value = catalogId
//            // last value = productId
//            int penultimateIndex = divided.length - 2;
//            return Integer.valueOf(divided[penultimateIndex]);
//        }
//        throw new UrlParseException("Couldn't parse id from product url " + productUrl);
//    }

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
    Integer parseCatalogId(String catalogUrl) {
        String[] divided = catalogUrl.split("/");
        int partCount = 5;
        if (divided.length == partCount) {
            int catalogIndex = divided.length - 1;
            return Integer.valueOf(divided[catalogIndex]);
        }
        throw new UrlParseException("Couldn't parse id from catalog url " + catalogUrl);
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
    Integer parseBrandId(String brandUrl) {
        String[] divided = brandUrl.split("/");
        int partCount = 5;
        if (divided.length == partCount) {
            int brandIndex = divided.length - 1;
            return Integer.valueOf(divided[brandIndex]);
        }
        throw new UrlParseException("Couldn't parse id from brand url " + brandUrl);
    }
}
