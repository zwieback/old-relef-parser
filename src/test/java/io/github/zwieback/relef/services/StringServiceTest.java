package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class StringServiceTest {

    private static final String EXPECTED_STRING = "abc";
    private static final String DIRTY_STRING = "\u00A0" + EXPECTED_STRING;
    private static final String SPECIAL_CHARS_IN_STRING = EXPECTED_STRING + "~!@#$%^&*()_+|{}:\"<>?№;-=\\[]',./";
    private static final String REPLACED_BY_UNDERSCORES_STRING = EXPECTED_STRING + "________________________________";
    private static final String WINDOWS_PATH_WITH_SPECIAL_CHARS = EXPECTED_STRING + "|<>:\"/\\|?*";
    private static final String NORMALIZED_WINDOWS_PATH = EXPECTED_STRING + "__________";
    private static final Long SOURCE_OBJECT = 100L;

    @SuppressWarnings("unused")
    @Autowired
    private StringService stringService;

    @Test
    public void test_clean_should_return_cleaned_string() {
        String cleaned = stringService.clean(DIRTY_STRING);
        assertThat(cleaned).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void test_clean_should_return_same_string() {
        String cleaned = stringService.clean(EXPECTED_STRING);
        assertThat(cleaned).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void test_replaceSpecialCharsByUnderscore_should_return_replaced_string() {
        String cleaned = stringService.replaceSpecialCharsByUnderscore(SPECIAL_CHARS_IN_STRING);
        assertThat(cleaned).isEqualTo(REPLACED_BY_UNDERSCORES_STRING);
    }

    @Test
    public void test_replaceSpecialCharsByUnderscore_should_return_same_string() {
        String cleaned = stringService.replaceSpecialCharsByUnderscore(EXPECTED_STRING);
        assertThat(cleaned).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void test_normalizeWindowsPath_should_normalize_path() {
        String normalized = stringService.normalizeWindowsPath(WINDOWS_PATH_WITH_SPECIAL_CHARS);
        assertThat(normalized).isEqualTo(NORMALIZED_WINDOWS_PATH);
    }

    @Test
    public void test_normalizeWindowsPath_should_return_same_string() {
        String normalized = stringService.normalizeWindowsPath(EXPECTED_STRING);
        assertThat(normalized).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void test_defaultString_should_return_same_string() {
        String result = stringService.defaultString(EXPECTED_STRING);
        assertThat(result).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void test_defaultString_should_return_empty_string() {
        String result = stringService.defaultString(null);
        assertThat(result).isEmpty();
    }

    @Test
    public void test_defaultString_object_should_return_string_representation_of_object() {
        String result = stringService.defaultString(SOURCE_OBJECT);
        assertThat(result).isEqualTo(SOURCE_OBJECT.toString());
    }

    @Test
    public void test_defaultString_object_should_return_empty_string() {
        String result = stringService.defaultString((Long) null);
        assertThat(result).isEmpty();
    }

    @Test
    public void test_parseToDouble_should_return_null() {
        assertThat(stringService.parseToDouble(null)).isNull();
        assertThat(stringService.parseToDouble("")).isNull();
    }

    @Test(expected = ParseException.class)
    public void test_parseToDouble_should_throw_exception() {
        stringService.parseToDouble("_abc_");
    }

    @Test
    public void test_parseToDouble_should_return_parsed_double() {
        assertThat(stringService.parseToDouble("123,456")).isEqualTo(123.456);
    }

    @Test
    public void test_parseToBoolean_should_return_null() {
        assertThat(stringService.parseToBoolean(null)).isNull();
        assertThat(stringService.parseToBoolean("")).isNull();
    }

    @Test
    public void test_parseToBoolean_should_return_parsed_boolean() {
        assertThat(stringService.parseToBoolean("да")).isTrue();
        assertThat(stringService.parseToBoolean("true")).isTrue();
        assertThat(stringService.parseToBoolean("нет")).isFalse();
    }
}
