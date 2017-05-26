package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.Manufacturer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ManufacturerMergerTest extends AbstractMergerTest<Manufacturer, String> {

    private static final String STR_ONE = "ONE";
    private static final String STR_TWO = "TWO";

    @SuppressWarnings("unused")
    @Autowired
    private ManufacturerMerger manufacturerMerger;

    @Override
    Merger<Manufacturer, String> getMerger() {
        return manufacturerMerger;
    }

    @Override
    Manufacturer buildEntityStubOne() {
        return buildEntityStub(STR_ONE, STR_ONE);
    }

    @Override
    Manufacturer buildEntityStubTwo() {
        return buildEntityStub(STR_TWO, STR_TWO);
    }

    @Override
    Manufacturer buildEntityStubOnePlusTwo() {
        return buildEntityStub(STR_ONE, STR_TWO);
    }

    private static Manufacturer buildEntityStub(@NotNull String name, @NotNull String url) {
        return new Manufacturer().setName(name).setUrl(url);
    }

    @Override
    void shouldBeEquals(Manufacturer mergedEntity, Manufacturer parsedEntity) {
        assertEquals(mergedEntity.getName(), parsedEntity.getName());
        assertEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
    }

    @Override
    void shouldBeNotEquals(Manufacturer mergedEntity, Manufacturer parsedEntity) {
        assertNotEquals(mergedEntity.getName(), parsedEntity.getName());
        assertNotEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
    }
}
