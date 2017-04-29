package io.github.zwieback.relef.services;

import io.github.zwieback.relef.services.HeadersBuilder.Headers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class HeadersBuilderTest {

    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    public void test_build_without_headers_should_be_empty() {
        Headers headers = HeadersBuilder.create().build();
        assertTrue(headers.isEmpty());
    }

    @Test
    public void test_build_with_headers_should_not_be_empty() {
        Headers headers = HeadersBuilder.create().add(KEY, VALUE).build();
        assertTrue(headers.containsKey(KEY));
        assertTrue(headers.get(KEY).equals(VALUE));
    }
}
