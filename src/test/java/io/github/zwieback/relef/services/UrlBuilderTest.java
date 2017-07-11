package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.PropertyConfig;
import io.github.zwieback.relef.configs.ServiceConfigForTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URISyntaxException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PropertyConfig.class,
        ServiceConfigForTest.class
})
public class UrlBuilderTest {

    private static final String INCORRECT_RELATIVE_URL = "A)*(&%(U N)V@*#&";
    private static final String DOMAIN_URL = "http://relefopt.ru/";
    private static final String PRODUCT_URL = "/69702/132654/";
    private static final String BRAND_URL = "/brands/12908290.php";
    private static final Long CATALOG_ID = 69702L;
    private static final Long PRODUCT_ID = 132654L;
    private static final Long NOW = 1492872488282L;
    private static final UUID PRODUCT_XML_ID = UUID.randomUUID();

    @SuppressWarnings("unused")
    @Autowired
    private UrlBuilder urlBuilder;

    @SuppressWarnings("unused")
    @Autowired
    private DateTimeService dateTimeService;

    @Test
    public void test_buildProductUrl_should_be_full_url() {
        String resultUrl = urlBuilder.buildProductUrl(CATALOG_ID, PRODUCT_ID);
        String expectedUrl = DOMAIN_URL + "catalog" + PRODUCT_URL;
        assertThat(resultUrl).isEqualTo(expectedUrl);
    }


    @Test
    public void test_buildTrickyProductUrl_should_contains_bxrand() {
        String resultUrl = urlBuilder.buildTrickyProductUrl(PRODUCT_URL);
        assertThat(resultUrl).contains("bxrand");
    }

    @Test
    public void test_buildTrickyProductUrl_should_contains_now_as_timestamp() {
        when(dateTimeService.nowAsMilliseconds()).thenReturn(NOW);

        String resultUrl = urlBuilder.buildTrickyProductUrl(PRODUCT_URL);
        String[] dividedUrl = resultUrl.split("="); // timestamp as ms in last value
        Long timestamp = Long.valueOf(dividedUrl[dividedUrl.length - 1]);
        assertThat(timestamp).isEqualTo(NOW);
    }


    @Test
    public void test_buildProductPhotoUrl_should_ends_with_xmlId_of_product() {
        String resultUrl = urlBuilder.buildProductPhotoUrl(PRODUCT_XML_ID);
        assertThat(resultUrl).endsWith(PRODUCT_XML_ID.toString());
    }


    @Test
    public void test_buildPathUrl_should_starts_with_domainUrl() {
        String resultUrl = urlBuilder.buildPathUrl(PRODUCT_URL);
        assertThat(resultUrl).startsWith(DOMAIN_URL);
    }

    @Test
    public void test_buildPathUrl_should_contains_productUrl() {
        String resultUrl = urlBuilder.buildPathUrl(PRODUCT_URL);
        assertThat(resultUrl).contains(PRODUCT_URL);
    }

    @Test
    public void test_buildPathUrl_should_ends_with_slash() {
        String resultUrl = urlBuilder.buildPathUrl(PRODUCT_URL);
        assertThat(resultUrl).endsWith("/");
    }


    @Test
    public void test_buildQueryUrl_should_starts_with_domainUrl() {
        String resultUrl = urlBuilder.buildPathUrl(PRODUCT_URL);
        assertThat(resultUrl).startsWith(DOMAIN_URL);
    }

    @Test
    public void test_buildQueryUrl_should_contains_brandUrl() {
        String resultUrl = urlBuilder.buildPathUrl(BRAND_URL);
        assertThat(resultUrl).contains(BRAND_URL);
    }

    @Test
    public void test_buildQueryUrl_should_not_ends_with_slash() {
        String resultUrl = urlBuilder.buildQueryUrl(BRAND_URL);
        assertThat(resultUrl).doesNotEndWith("/");
    }

    @Test(expected = URISyntaxException.class)
    public void test_buildQueryUrl_should_throw_exception() {
        urlBuilder.buildQueryUrl(INCORRECT_RELATIVE_URL);
    }
}
