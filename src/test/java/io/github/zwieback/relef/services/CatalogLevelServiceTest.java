package io.github.zwieback.relef.services;

import io.github.zwieback.relef.configs.ServiceConfig;
import io.github.zwieback.relef.entities.CatalogLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public class CatalogLevelServiceTest {

    @SuppressWarnings("unused")
    @Autowired
    private CatalogLevelService catalogLevelService;

    @Test
    public void test_collectActiveCatalogLevels_should_return_only_active_levels() {
        Set<CatalogLevel> activeLevels = EnumSet.of(CatalogLevel.FIRST, CatalogLevel.SECOND, CatalogLevel.THIRD,
                CatalogLevel.FOURTH);
        Set<CatalogLevel> levels = catalogLevelService.collectActiveCatalogLevels();
        activeLevels.forEach(level -> assertTrue(levels.contains(level)));
        assertEquals(CatalogLevel.values().length - 1, levels.size());
    }

    @Test
    public void test_collectActiveCatalogLevels_should_not_contains_NONE() {
        Set<CatalogLevel> levels = catalogLevelService.collectActiveCatalogLevels();
        assertFalse(levels.contains(CatalogLevel.NONE));
    }

    @Test
    public void test_determineLastCatalogLevel_should_return_same_string() {
        CatalogLevel lastLevel = catalogLevelService.determineLastCatalogLevel();
        assertEquals(CatalogLevel.FOURTH, lastLevel);
    }
}
