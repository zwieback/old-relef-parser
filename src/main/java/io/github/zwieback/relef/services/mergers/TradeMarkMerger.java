package io.github.zwieback.relef.services.mergers;

import io.github.zwieback.relef.entities.TradeMark;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class TradeMarkMerger extends Merger<TradeMark, String> {

    @Override
    public TradeMark merge(@NotNull TradeMark existedTradeMark, @NotNull TradeMark parsedTradeMark) {
        if (!parsedTradeMark.getName().equals(existedTradeMark.getName())) {
            return parsedTradeMark;
        }
        if (needToMerge(existedTradeMark.getUrl(), parsedTradeMark.getUrl())) {
            existedTradeMark.setUrl(parsedTradeMark.getUrl());
        }
        return existedTradeMark;
    }

    @NotNull
    @Override
    String getId(TradeMark tradeMark) {
        return tradeMark.getName();
    }
}
