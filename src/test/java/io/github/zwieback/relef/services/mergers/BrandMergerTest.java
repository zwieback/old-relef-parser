package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Brand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BrandMergerTest extends AbstractMergerTest<Brand, Long> {

    private static final Long ID_ONE = 1L;
    private static final Long ID_TWO = 2L;
    private static final String STR_ONE = "name ONE";
    private static final String STR_TWO = "name TWO";

    @SuppressWarnings("unused")
    @Autowired
    private BrandMerger brandMerger;

    @Override
    Merger<Brand, Long> getMerger() {
        return brandMerger;
    }

    @Override
    Brand buildEntityStubOne() {
        return buildEntityStub(ID_ONE, STR_ONE, null, null);
    }

    @Override
    Brand buildEntityStubTwo() {
        return buildEntityStub(ID_TWO, STR_TWO, STR_TWO, STR_TWO);
    }

    @Override
    Brand buildEntityStubOnePlusTwo() {
        return buildEntityStub(ID_ONE, STR_TWO, STR_TWO, STR_TWO);
    }

    private static Brand buildEntityStub(@NotNull Long id, @Nullable String name, @Nullable String url,
                                         @Nullable String imageUrl) {
        return new Brand().setId(id).setName(name).setUrl(url).setImageUrl(imageUrl);
    }

    @Override
    void shouldBeEquals(Brand mergedEntity, Brand parsedEntity) {
        String[] fieldsToIgnore = {"lastUpdate"};
        assertThat(parsedEntity).isEqualToIgnoringGivenFields(mergedEntity, fieldsToIgnore);
    }

    @Override
    void shouldBeNotEquals(Brand mergedEntity, Brand parsedEntity) {
        assertThat(parsedEntity.getId()).isNotEqualTo(mergedEntity.getId());
        assertThat(parsedEntity.getName()).isNotEqualTo(mergedEntity.getName());
        assertThat(parsedEntity.getUrl()).isNotEqualTo(mergedEntity.getUrl());
        assertThat(parsedEntity.getImageUrl()).isNotEqualTo(mergedEntity.getImageUrl());
    }
}
