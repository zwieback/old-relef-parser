package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class StringServiceTest {

    private static final String EXPECTED_STRING = "abc";
    private static final String DIRTY_STRING = "\u00A0" + EXPECTED_STRING;

    @SuppressWarnings("unused")
    @Autowired
    private StringService stringService;

    @Test
    public void test_clean_should_return_cleaned_string() {
        String cleaned = stringService.clean(DIRTY_STRING);
        Assert.assertEquals(EXPECTED_STRING, cleaned);
    }

    @Test
    public void test_clean_should_return_same_string() {
        String cleaned = stringService.clean(EXPECTED_STRING);
        Assert.assertEquals(EXPECTED_STRING, cleaned);
    }
}
