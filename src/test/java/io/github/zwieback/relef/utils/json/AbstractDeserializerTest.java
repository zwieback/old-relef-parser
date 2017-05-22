package io.github.zwieback.relef.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.zwieback.relef.configs.JacksonConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        JacksonConfig.class
})
public abstract class AbstractDeserializerTest {

    @SuppressWarnings("unused")
    @Autowired
    private ResourceLoader resourceLoader;

    @SuppressWarnings("unused")
    @Autowired
    private ObjectMapper mapper;

    <T> T readValue(String fileName, Class<T> clazz) throws IOException {
        Resource resource = resourceLoader.getResource(fileName);
        try (InputStream inputStream = resource.getInputStream()) {
            return mapper.readValue(inputStream, clazz);
        }
    }
}
