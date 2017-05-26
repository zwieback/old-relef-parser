package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CatalogMergerTest extends AbstractMergerTest<Catalog, Long> {

    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final String STR_ONE = "ONE";
    private static final String STR_TWO = "TWO";
    private static final CatalogLevel LEVEL_ONE = CatalogLevel.FIRST;
    private static final CatalogLevel LEVEL_TWO = CatalogLevel.FOURTH;

    @SuppressWarnings("unused")
    @Autowired
    private CatalogMerger catalogMerger;

    @Override
    Merger<Catalog, Long> getMerger() {
        return catalogMerger;
    }

    @Override
    Catalog buildEntityStubOne() {
        return buildEntityStub(ID_ONE, STR_ONE, STR_ONE, LEVEL_ONE, null);
    }

    @Override
    Catalog buildEntityStubTwo() {
        return buildEntityStub(ID_TWO, STR_TWO, STR_TWO, LEVEL_TWO, buildExistedEntityStub());
    }

    @Override
    Catalog buildEntityStubOnePlusTwo() {
        return buildEntityStub(ID_ONE, STR_TWO, STR_TWO, LEVEL_TWO, buildExistedEntityStub());
    }

    private static Catalog buildExistedEntityStub() {
        return buildEntityStub(ID_ONE, STR_ONE, STR_ONE, LEVEL_ONE, null);
    }

    private static Catalog buildEntityStub(@NotNull Long id, @NotNull String name, @NotNull String url,
                                           @NotNull CatalogLevel level, @Nullable Catalog parent) {
        return new Catalog().setId(id).setName(name).setUrl(url).setLevel(level).setParent(parent);
    }

    @Override
    void shouldBeEquals(Catalog mergedEntity, Catalog parsedEntity) {
        assertEquals(mergedEntity.getId(), parsedEntity.getId());
        assertEquals(mergedEntity.getName(), parsedEntity.getName());
        assertEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
        assertEquals(mergedEntity.getLevel(), parsedEntity.getLevel());
        assertEquals(mergedEntity.getParent(), parsedEntity.getParent());
    }

    @Override
    void shouldBeNotEquals(Catalog mergedEntity, Catalog parsedEntity) {
        assertNotEquals(mergedEntity.getId(), parsedEntity.getId());
        assertNotEquals(mergedEntity.getName(), parsedEntity.getName());
        assertNotEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
        assertNotEquals(mergedEntity.getLevel(), parsedEntity.getLevel());
        assertNotEquals(mergedEntity.getParent(), parsedEntity.getParent());
    }
}
