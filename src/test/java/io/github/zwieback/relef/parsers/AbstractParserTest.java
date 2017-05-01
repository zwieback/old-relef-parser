package io.github.zwieback.relef.parsers;

import io.github.zwieback.relef.configs.DatabaseConfig;
import io.github.zwieback.relef.configs.ParserConfigForTest;
import io.github.zwieback.relef.configs.PropertyConfig;
import io.github.zwieback.relef.configs.ServiceConfig;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DatabaseConfig.class,
        ParserConfigForTest.class,
        PropertyConfig.class,
        ServiceConfig.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
abstract class AbstractParserTest {

    @SuppressWarnings("unused")
    @Autowired
    private FileParser fileParser;

    @SuppressWarnings("unused")
    @Autowired
    private InternalParser internalParser;

    @SuppressWarnings("unused")
    @Autowired
    private ResourceLoader resourceLoader;

    @Before
    public void setUp() throws IOException {
        Resource resource = resourceLoader.getResource(getResourcePage());
        InputStream inputStream = resource.getInputStream();
        Document document = fileParser.parseInputStream(inputStream);

        when(internalParser.post(anyString(), anyObject())).thenReturn(document);
        when(internalParser.get(anyString(), anyObject())).thenReturn(document);
        when(internalParser.get(anyString())).thenReturn(document);
    }

    abstract String getResourcePage();
}
