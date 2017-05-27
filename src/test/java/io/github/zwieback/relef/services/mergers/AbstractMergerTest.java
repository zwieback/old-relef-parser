package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.configs.ServiceConfig;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ServiceConfig.class
})
public abstract class AbstractMergerTest<T, ID> {

    abstract Merger<T, ID> getMerger();

    @Test
    public void test_merge_should_merge_entities() {
        List<T> existedEntities = buildEntitiesStubOne();
        List<T> parsedEntities = buildEntitiesStubOnePlusTwo();
        List<T> mergedEntities = getMerger().merge(existedEntities, parsedEntities);
        assertEquals(mergedEntities.size(), parsedEntities.size());
        shouldBeEquals(mergedEntities.get(0), parsedEntities.get(0));
    }

    @Test
    public void test_merge_should_not_merge_due_to_empty_existed_entities() {
        List<T> existedEntities = Collections.emptyList();
        List<T> parsedEntities = buildEntitiesStubTwo();
        List<T> mergedEntities = getMerger().merge(existedEntities, parsedEntities);
        assertEquals(mergedEntities.size(), parsedEntities.size());
        shouldBeEquals(mergedEntities.get(0), parsedEntities.get(0));
    }

    @Test
    public void test_merge_should_not_merge_due_to_different_names() {
        T existedEntity = buildEntityStubOne();
        T parsedEntity = buildEntityStubTwo();
        T mergedEntity = getMerger().merge(existedEntity, parsedEntity);
        shouldBeNotEquals(mergedEntity, existedEntity);
    }

    @NotNull
    private List<T> buildEntitiesStubOne() {
        return Collections.singletonList(buildEntityStubOne());
    }

    @NotNull
    private List<T> buildEntitiesStubTwo() {
        return Collections.singletonList(buildEntityStubTwo());
    }

    @NotNull
    private List<T> buildEntitiesStubOnePlusTwo() {
        return Collections.singletonList(buildEntityStubOnePlusTwo());
    }

    abstract T buildEntityStubOne();

    abstract T buildEntityStubTwo();

    abstract T buildEntityStubOnePlusTwo();

    abstract void shouldBeEquals(T mergedEntity, T parsedEntity);

    abstract void shouldBeNotEquals(T mergedEntity, T parsedEntity);
}
