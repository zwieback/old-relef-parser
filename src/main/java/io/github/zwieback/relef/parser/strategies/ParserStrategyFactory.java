package io.github.zwieback.relef.parser.strategies;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.CATALOG_STRATEGY;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_FAST;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_HYBRID;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_SLOW;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.PRODUCT_STRATEGY;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_CATALOG;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_FULL;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PARSER_PRODUCT;

@Service
public class ParserStrategyFactory {

    private final ParserStrategy fullStrategyFast;
    private final ParserStrategy fullStrategySlow;
    private final ParserStrategy fullStrategyHybrid;
    private final ParserStrategy catalogStrategy;
    private final ParserStrategy productStrategy;

    private FullParserStrategyType fullParserStrategyType;
    private String catalogIds;
    private String productIds;

    @Autowired
    public ParserStrategyFactory(@Qualifier(FULL_STRATEGY_FAST) ParserStrategy fullStrategyFast,
                                 @Qualifier(FULL_STRATEGY_SLOW) ParserStrategy fullStrategySlow,
                                 @Qualifier(FULL_STRATEGY_HYBRID) ParserStrategy fullStrategyHybrid,
                                 @Qualifier(CATALOG_STRATEGY) ParserStrategy catalogStrategy,
                                 @Qualifier(PRODUCT_STRATEGY) ParserStrategy productStrategy) {
        this.fullStrategyFast = fullStrategyFast;
        this.fullStrategySlow = fullStrategySlow;
        this.fullStrategyHybrid = fullStrategyHybrid;
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
                case "2":
                    fullParserStrategyType = FullParserStrategyType.HYBRID;
                    break;
                default:
                    fullParserStrategyType = FullParserStrategyType.FAST;
                    break;
            }
        } else {
            fullParserStrategyType = FullParserStrategyType.NONE;
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
            case FAST:
                strategies.add(fullStrategyFast);
                break;
            case HYBRID:
                strategies.add(fullStrategyHybrid);
                break;
        }
        return strategies;
    }
}
