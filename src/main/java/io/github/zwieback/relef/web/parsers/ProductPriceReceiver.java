package io.github.zwieback.relef.web.parsers;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import io.github.zwieback.relef.web.converters.HeadersToMultiValueMapConverter;
import io.github.zwieback.relef.web.rest.services.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

@Service
public class ProductPriceReceiver {

    private final RestService restService;
    private final HeadersToMultiValueMapConverter converter;

    @Value("${site.domain.price}")
    private String priceUrl;

    @Autowired
    public ProductPriceReceiver(RestService restService, HeadersToMultiValueMapConverter converter) {
        this.restService = restService;
        this.converter = converter;
    }

    public ProductPricesDto getPrices(List<Product> products) {
        Headers queryParams = buildBody(products);
        MultiValueMap<String, String> body = converter.convert(queryParams);
        HttpHeaders httpHeaders = restService.buildHeaders();
        HttpEntity httpEntity = restService.buildEntity(body, httpHeaders);
        return restService.post(priceUrl, httpEntity, ProductPricesDto.class);
    }

    private static Headers buildBody(List<Product> products) {
        HeadersBuilder builder = HeadersBuilder.create().add("action", "define");
        products.forEach(product -> {
            if (Objects.nonNull(product.getXmlId()) && Objects.nonNull(product.getDataType())) {
                builder
                        .add(String.format("product[%d][type]", product.getId()), product.getDataType())
                        .add(String.format("product[%d][xmlid]", product.getId()), product.getXmlId().toString());
            }
        });
        return builder.build();
    }
}
