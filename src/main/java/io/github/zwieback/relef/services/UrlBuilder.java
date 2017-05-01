package io.github.zwieback.relef.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlBuilder {

    private static final Logger log = LogManager.getLogger(UrlBuilder.class);

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

    @NotNull
    private String buildUrl(String relativeUrl, boolean addPostfixDelimiter) {
        String path = domainUrl + relativeUrl + (addPostfixDelimiter ? "/" : "");
        try {
            return new URI(path).normalize().toString();
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
