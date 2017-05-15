package io.github.zwieback.relef.parsers.strategies;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.CATALOG_STRATEGY;
import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY_FAST;
import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY_SLOW;
import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.PRODUCT_STRATEGY;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_CATALOG;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_FULL;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_PRODUCT;

@Service
public class ParserStrategyFactory {

    private final ParserStrategy fullStrategyFast;
    private final ParserStrategy fullStrategySlow;
    private final ParserStrategy catalogStrategy;
    private final ParserStrategy productStrategy;

    private FullParserStrategyType fullParserStrategyType;
    private String catalogIds;
    private String productIds;

    @Autowired
    public ParserStrategyFactory(@Qualifier(FULL_STRATEGY_FAST) ParserStrategy fullStrategyFast,
                                 @Qualifier(FULL_STRATEGY_SLOW) ParserStrategy fullStrategySlow,
                                 @Qualifier(CATALOG_STRATEGY) ParserStrategy catalogStrategy,
                                 @Qualifier(PRODUCT_STRATEGY) ParserStrategy productStrategy) {
        this.fullStrategyFast = fullStrategyFast;
        this.fullStrategySlow = fullStrategySlow;
        this.catalogStrategy = catalogStrategy;
        this.productStrategy = productStrategy;
    }

    public void parseCommandLine(CommandLine cmd) {
        if (cmd.hasOption(OPTION_PARSER_CATALOG)) {
            catalogIds = cmd.getOptionValue(OPTION_PARSER_CATALOG);
        }
        if (cmd.hasOption(OPTION_PARSER_PRODUCT)) {
            productIds = cmd.getOptionValue(OPTION_PARSER_PRODUCT);
        }
        if (cmd.hasOption(OPTION_PARSER_FULL)) {
            switch (cmd.getOptionValue(OPTION_PARSER_FULL)) {
                case "1":
                    fullParserStrategyType = FullParserStrategyType.SLOW;
                    break;
                default:
                    fullParserStrategyType = FullParserStrategyType.FAST;
                    break;
            }
        }
    }

    public List<ParserStrategy> createStrategies() {
        List<ParserStrategy> strategies = new ArrayList<>();
        if (!StringUtils.isEmpty(catalogIds)) {
            catalogStrategy.setEntityIds(catalogIds);
            strategies.add(catalogStrategy);
        }
        if (!StringUtils.isEmpty(productIds)) {
            productStrategy.setEntityIds(productIds);
            strategies.add(productStrategy);
        }
        switch (fullParserStrategyType) {
            case SLOW:
                strategies.add(fullStrategySlow);
                break;
            default:
                strategies.add(fullStrategyFast);
                break;
        }
        return strategies;
    }
}
