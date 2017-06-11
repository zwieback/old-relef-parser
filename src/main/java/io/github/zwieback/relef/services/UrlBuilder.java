package io.github.zwieback.relef.services;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class UrlBuilder {

    private final DateTimeService dateTimeService;

    @Value("${site.domain}")
    private String domainUrl;

    @Autowired
    public UrlBuilder(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    /**
     * Build product url.
     *
     * @param catalogId catalog id
     * @param productId product id
     * @return product url
     */
    @NotNull
    public String buildProductUrl(@NotNull Long catalogId, @NotNull Long productId) {
        String relativeUrl = "catalog/" + catalogId + "/" + productId + "/";
        return buildPathUrl(relativeUrl);
    }

    /**
     * Add bxrand query param to productUrl with current timestamp.
     *
     * @param productUrl product url
     * @return tricky url for product request
     */
    @NotNull
    public String buildTrickyProductUrl(String productUrl) {
        return productUrl + "?bxrand=" + dateTimeService.nowAsMilliseconds();
    }

    /**
     * Build a product photo url.
     *
     * @param productXmlId xml id of product
     * @return url to photo of product
     */
    @NotNull
    public String buildProductPhotoUrl(@NotNull UUID productXmlId) {
        return buildQueryUrl("getimage.php?guid=" + productXmlId.toString());
    }

    /**
     * Domain url + relative url + slash if needed.
     * <p>
     * Examples:
     * <ul>
     * <li>http://relefopt.ru/catalog/69472/321940415/</li>
     * </ul>
     *
     * @param relativeUrl relative url
     * @return domain url concatenating with relative url
     */
    @NotNull
    public String buildPathUrl(String relativeUrl) {
        return buildUrl(relativeUrl, true);
    }

    /**
     * Domain url + relative url.
     * <p>
     * Examples:
     * <ul>
     * <li>http://relefopt.ru/upload/iblock/7e6/397de5b3_0fa3_11e7_9405_acf1df3ff64b_0b4db48c_acab_4059_ab8c_93993bdbb153.jpg</li>
     * <li>http://relefopt.ru/catalog/69472/?arrFilter_pf[DP_MANIFACTUR][12861056]=12861056</li>
     * <li>http://relefopt.ru/brands/12908290.php</li>
     * </ul>
     *
     * @param relativeUrl relative url
     * @return domain url concatenating with relative url
     */
    @NotNull
    public String buildQueryUrl(String relativeUrl) {
        return buildUrl(relativeUrl, false);
    }

    @SneakyThrows(URISyntaxException.class)
    @NotNull
    private String buildUrl(String relativeUrl, boolean addPostfixDelimiter) {
        String path = domainUrl + relativeUrl + (addPostfixDelimiter ? "/" : "");
        return new URI(path).normalize().toString();
    }
}
