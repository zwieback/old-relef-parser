package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class StringServiceTest {

    private static final String EMPTY_STRING = "";
    private static final String EXPECTED_STRING = "abc";
    private static final String DIRTY_STRING = "\u00A0" + EXPECTED_STRING;
    private static final String SPECIAL_CHARS_IN_STRING = EXPECTED_STRING + "~!@#$%^&*()_+|{}:\"<>?№;-=\\[]',./";
    private static final String REPLACED_BY_UNDERSCORES_STRING = EXPECTED_STRING + "________________________________";
    private static final Long SOURCE_OBJECT = 100L;

    @SuppressWarnings("unused")
    @Autowired
    private StringService stringService;

    @Test
    public void test_clean_should_return_cleaned_string() {
        String cleaned = stringService.clean(DIRTY_STRING);
        assertEquals(EXPECTED_STRING, cleaned);
    }

    @Test
    public void test_clean_should_return_same_string() {
        String cleaned = stringService.clean(EXPECTED_STRING);
        assertEquals(EXPECTED_STRING, cleaned);
    }

    @Test
    public void test_replaceSpecialCharsByUnderscore_should_return_replaced_string() {
        String cleaned = stringService.replaceSpecialCharsByUnderscore(SPECIAL_CHARS_IN_STRING);
        assertEquals(REPLACED_BY_UNDERSCORES_STRING, cleaned);
    }

    @Test
    public void test_replaceSpecialCharsByUnderscore_should_return_same_string() {
        String cleaned = stringService.replaceSpecialCharsByUnderscore(EXPECTED_STRING);
        assertEquals(EXPECTED_STRING, cleaned);
    }

    @Test
    public void test_defaultString_should_return_same_string() {
        String result = stringService.defaultString(EXPECTED_STRING);
        assertEquals(EXPECTED_STRING, result);
    }

    @Test
    public void test_defaultString_should_return_empty_string() {
        String result = stringService.defaultString(null);
        assertEquals(EMPTY_STRING, result);
    }

    @Test
    public void test_defaultString_object_should_return_string_representation_of_object() {
        String result = stringService.defaultString(SOURCE_OBJECT);
        assertEquals(SOURCE_OBJECT.toString(), result);
    }

    @Test
    public void test_defaultString_object_should_return_empty_string() {
        String result = stringService.defaultString((Long) null);
        assertEquals(EMPTY_STRING, result);
    }
}
