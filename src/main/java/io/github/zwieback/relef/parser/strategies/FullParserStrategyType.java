package io.github.zwieback.relef.parser.strategies;

public enum FullParserStrategyType {

    /**
     * not use parser
     */
    NONE,

    /**
     * parse products from catalog pages only
     */
    FAST,

    /**
     * parse each product from its own page
     */
    SLOW,

    /**
     * parse products from catalog pages and
     * if product has no description then
     * parse product from its own page
     */
    HYBRID
}
