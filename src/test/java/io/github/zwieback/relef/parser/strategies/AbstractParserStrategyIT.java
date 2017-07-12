package io.github.zwieback.relef.parser.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import io.github.zwieback.relef.configs.*;
import io.github.zwieback.relef.entities.dto.product.prices.ProductPricesDto;
import io.github.zwieback.relef.parsers.FileParser;
import io.github.zwieback.relef.parsers.InternalParser;
import io.github.zwieback.relef.web.rest.services.RestService;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DatabaseConfigForTest.class,
        JacksonConfig.class,
        ParserConfigForTest.class,
        ParserStrategyConfig.class,
        PropertyConfig.class,
        ServiceConfig.class,
        WebConfigForTest.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestExecutionListeners({
        DbUnitTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class
})
public abstract class AbstractParserStrategyIT {

    @SuppressWarnings("unused")
    @Autowired
    private ResourceLoader resourceLoader;

    @SuppressWarnings("unused")
    @Autowired
    private ObjectMapper mapper;

    @SuppressWarnings("unused")
    @Autowired
    private FileParser fileParser;

    @SuppressWarnings("unused")
    @Autowired
    private RestService restService;

    @SuppressWarnings("unused")
    @Autowired
    private InternalParser internalParser;

    @Before
    public void setUp() {
        Document document = getDocument();
        when(internalParser.post(anyString(), anyObject())).thenReturn(document);
        when(internalParser.get(anyString(), anyObject())).thenReturn(document);
        when(internalParser.get(anyString())).thenReturn(document);

        ProductPricesDto productPrices = readValue(getProductPricesFileName(), ProductPricesDto.class);
        when(restService.post(anyString(), anyObject(), anyObject())).thenReturn(productPrices);
    }

    abstract String getResourcePage();

    @SneakyThrows(IOException.class)
    private Document getDocument() {
        Resource resource = resourceLoader.getResource(getResourcePage());
        InputStream inputStream = resource.getInputStream();
        return fileParser.parseInputStream(inputStream);
    }

    abstract String getProductPricesFileName();

    @SneakyThrows(IOException.class)
    private <T> T readValue(String fileName, Class<T> clazz) {
        Resource resource = resourceLoader.getResource(fileName);
        @Cleanup InputStream inputStream = resource.getInputStream();
        return mapper.readValue(inputStream, clazz);
    }
}
