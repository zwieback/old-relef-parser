package io.github.zwieback.relef.services;

import io.github.zwieback.relef.entities.CatalogLevel;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Set;

@Service
public class CatalogLevelService {

    /**
     * Collect and return set of active catalog levels.
     * Active is all levels except NONE.
     *
     * @return active catalog levels
     */
    public Set<CatalogLevel> collectActiveCatalogLevels() {
        Set<CatalogLevel> levels = EnumSet.allOf(CatalogLevel.class);
        levels.remove(CatalogLevel.NONE);
        return levels;
    }

    /**
     * Determine last catalog level.
     * The last level is a level that does not have the next level, starting with the FIRST (except NONE).
     *
     * @return last catalog level
     */
    public CatalogLevel determineLastCatalogLevel() {
        CatalogLevel level = CatalogLevel.FIRST;
        while (level.hasNext()) {
            level = level.next();
        }
        return level;
    }
}
