package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.TradeMark;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TradeMarkMergerTest extends AbstractMergerTest<TradeMark, String> {

    private static final String STR_ONE = "ONE";
    private static final String STR_TWO = "TWO";

    @SuppressWarnings("unused")
    @Autowired
    private TradeMarkMerger tradeMarkMerger;

    @Override
    Merger<TradeMark, String> getMerger() {
        return tradeMarkMerger;
    }

    @Override
    TradeMark buildEntityStubOne() {
        return buildEntityStub(STR_ONE, STR_ONE);
    }

    @Override
    TradeMark buildEntityStubTwo() {
        return buildEntityStub(STR_TWO, STR_TWO);
    }

    @Override
    TradeMark buildEntityStubOnePlusTwo() {
        return buildEntityStub(STR_ONE, STR_TWO);
    }

    private static TradeMark buildEntityStub(@NotNull String name, @NotNull String url) {
        return new TradeMark().setName(name).setUrl(url);
    }

    @Override
    void shouldBeEquals(TradeMark mergedEntity, TradeMark parsedEntity) {
        assertEquals(mergedEntity.getName(), parsedEntity.getName());
        assertEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
    }

    @Override
    void shouldBeNotEquals(TradeMark mergedEntity, TradeMark parsedEntity) {
        assertNotEquals(mergedEntity.getName(), parsedEntity.getName());
        assertNotEquals(mergedEntity.getUrl(), parsedEntity.getUrl());
    }
}
