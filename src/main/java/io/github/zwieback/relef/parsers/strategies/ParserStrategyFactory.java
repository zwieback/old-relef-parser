package io.github.zwieback.relef.parsers.strategies;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.CATALOG_STRATEGY;
import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.FULL_STRATEGY;
import static io.github.zwieback.relef.parsers.strategies.ParserStrategy.PRODUCT_STRATEGY;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_CATALOG_IDS;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_PRODUCT_IDS;

@Service
public class ParserStrategyFactory {

    private final ParserStrategy fullStrategy;
    private final ParserStrategy catalogStrategy;
    private final ParserStrategy productStrategy;

    private String catalogIds;
    private String productIds;

    @Autowired
    public ParserStrategyFactory(@Qualifier(FULL_STRATEGY) ParserStrategy fullStrategy,
                                 @Qualifier(CATALOG_STRATEGY) ParserStrategy catalogStrategy,
                                 @Qualifier(PRODUCT_STRATEGY) ParserStrategy productStrategy) {
        this.fullStrategy = fullStrategy;
        this.catalogStrategy = catalogStrategy;
        this.productStrategy = productStrategy;
    }

    public void parseCommandLine(CommandLine cmd) {
        if (cmd.hasOption(OPTION_CATALOG_IDS)) {
            catalogIds = cmd.getOptionValue(OPTION_CATALOG_IDS);
        }
        if (cmd.hasOption(OPTION_PRODUCT_IDS)) {
            productIds = cmd.getOptionValue(OPTION_PRODUCT_IDS);
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
        if (StringUtils.isEmpty(catalogIds) && StringUtils.isEmpty(productIds)) {
            strategies.add(fullStrategy);
        }
        return strategies;
    }
}
