package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.TradeMark;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

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
        String[] fieldsToIgnore = {"lastUpdate"};
        assertThat(parsedEntity).isEqualToIgnoringGivenFields(mergedEntity, fieldsToIgnore);
    }

    @Override
    void shouldBeNotEquals(TradeMark mergedEntity, TradeMark parsedEntity) {
        assertThat(parsedEntity.getName()).isNotEqualTo(mergedEntity.getName());
        assertThat(parsedEntity.getUrl()).isNotEqualTo(mergedEntity.getUrl());
    }
}
