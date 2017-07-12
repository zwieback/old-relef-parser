package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.entities.CatalogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

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
        String[] fieldsToIgnore = {"lastUpdate"};
        assertThat(parsedEntity).isEqualToIgnoringGivenFields(mergedEntity, fieldsToIgnore);
    }

    @Override
    void shouldBeNotEquals(Catalog mergedEntity, Catalog parsedEntity) {
        assertThat(parsedEntity.getId()).isNotEqualTo(mergedEntity.getId());
        assertThat(parsedEntity.getName()).isNotEqualTo(mergedEntity.getName());
        assertThat(parsedEntity.getUrl()).isNotEqualTo(mergedEntity.getUrl());
        assertThat(parsedEntity.getLevel()).isNotEqualTo(mergedEntity.getLevel());
        assertThat(parsedEntity.getParent()).isNotEqualTo(mergedEntity.getParent());
    }
}
