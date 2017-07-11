package io.github.zwieback.relef.web.converters;

import io.github.zwieback.relef.configs.WebConfigForTest;
import io.github.zwieback.relef.services.HeadersBuilder;
import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        WebConfigForTest.class
})
public class HeadersToMultiValueMapConverterTest {

    @SuppressWarnings("unused")
    @Autowired
    private HeadersToMultiValueMapConverter converter;

    @Test
    public void test_result_of_convert_should_be_empty() {
        Headers headers = HeadersBuilder.create().build();
        MultiValueMap<String, String> convertedMap = converter.convert(headers);
        assertThat(headers).isEmpty();
        assertThat(convertedMap).isEmpty();
    }

    @Test
    public void test_convert_should_return_same_keys_and_values() {
        Headers headers = HeadersBuilder.create()
                .add("key1", "value1")
                .add("key2", "value2")
                .add("key3", "value3")
                .build();
        MultiValueMap<String, String> convertedMap = converter.convert(headers);
        assertThat(headers).hasSameSizeAs(convertedMap);
        headers.forEach((key, value) -> {
            assertThat(convertedMap).containsKey(key);
            assertThat(convertedMap.getFirst(key)).isEqualTo(value);
        });
    }
}
