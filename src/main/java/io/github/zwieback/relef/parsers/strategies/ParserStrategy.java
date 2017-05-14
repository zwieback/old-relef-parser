package io.github.zwieback.relef.parsers.strategies;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ParserStrategy {

    static final String FULL_STRATEGY_FAST = "fullStrategyFast";
    static final String FULL_STRATEGY_SLOW = "fullStrategySlow";
    static final String CATALOG_STRATEGY = "catalogStrategy";
    static final String PRODUCT_STRATEGY = "productStrategy";

    private static final String DELIMITER = ",";

    /**
     * Used for parsing only this entities.
     *
     * @param entityIds list of entity id (divided by delimiter)
     * @throws UnsupportedOperationException if the set operation is not supported by strategy
     * @throws NumberFormatException         if the string cannot be parsed as a {@code long}
     */
    abstract void setEntityIds(String entityIds);

    List<Long> collectLongIds(String entityIds) {
        return Arrays.stream(entityIds.split(DELIMITER)).map(Long::valueOf).collect(Collectors.toList());
    }

    public abstract void parse();
}
