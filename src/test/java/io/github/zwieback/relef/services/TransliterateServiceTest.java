package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class TransliterateServiceTest {

    private static final String PANGRAM_STRING = "Эй, жлоб! Где туз? Прячь юных съёмщиц в шкаф.";
    private static final String TRANSLITERATED_STRING = "Ei, zhlob! Gde tuz? Priach iunykh sieemshchits v shkaf.";

    @SuppressWarnings("unused")
    @Autowired
    private TransliterateService transliterateService;

    @Test
    public void test_transliterate_should_return_transliterated_string() {
        String transliterated = transliterateService.transliterate(PANGRAM_STRING);
        assertThat(transliterated).isEqualTo(TRANSLITERATED_STRING);
    }
}
