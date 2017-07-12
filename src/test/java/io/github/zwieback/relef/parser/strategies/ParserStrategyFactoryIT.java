package io.github.zwieback.relef.parser.strategies;

import io.github.zwieback.relef.configs.*;
import io.github.zwieback.relef.services.CommandLineService;
import org.apache.commons.cli.CommandLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static io.github.zwieback.relef.parser.strategies.ParserStrategy.CATALOG_STRATEGY;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_FAST;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_HYBRID;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.FULL_STRATEGY_SLOW;
import static io.github.zwieback.relef.parser.strategies.ParserStrategy.PRODUCT_STRATEGY;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DatabaseConfigForTest.class,
        ParserConfigForTest.class,
        ParserStrategyConfig.class,
        PropertyConfig.class,
        ServiceConfig.class,
        WebConfigForTest.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParserStrategyFactoryIT {

    @SuppressWarnings("unused")
    @Autowired
    private CommandLineService cmdService;

    @SuppressWarnings("unused")
    @Autowired
    private ParserStrategyFactory parserStrategyFactory;

    @SuppressWarnings("unused")
    @Autowired
    @Qualifier(FULL_STRATEGY_FAST)
    private ParserStrategy fullStrategyFast;

    @SuppressWarnings("unused")
    @Autowired
    @Qualifier(FULL_STRATEGY_SLOW)
    private ParserStrategy fullStrategySlow;

    @SuppressWarnings("unused")
    @Autowired
    @Qualifier(FULL_STRATEGY_HYBRID)
    private ParserStrategy fullStrategyHybrid;

    @SuppressWarnings("unused")
    @Autowired
    @Qualifier(CATALOG_STRATEGY)
    private ParserStrategy catalogStrategy;

    @SuppressWarnings("unused")
    @Autowired
    @Qualifier(PRODUCT_STRATEGY)
    private ParserStrategy productStrategy;

    @Test
    public void test_createStrategies_should_return_fast_full_strategy() {
        CommandLine cmd = createCommandLine("-" + CommandLineService.OPTION_PARSER_FULL, "0");
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).hasSize(1);
        assertThat(parserStrategies).containsExactly(fullStrategyFast);
    }

    @Test
    public void test_createStrategies_should_return_slow_full_strategy() {
        CommandLine cmd = createCommandLine("-" + CommandLineService.OPTION_PARSER_FULL, "1");
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).hasSize(1);
        assertThat(parserStrategies).containsExactly(fullStrategySlow);
    }

    @Test
    public void test_createStrategies_should_return_hybrid_full_strategy() {
        CommandLine cmd = createCommandLine("-" + CommandLineService.OPTION_PARSER_FULL, "2");
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).hasSize(1);
        assertThat(parserStrategies).containsExactly(fullStrategyHybrid);
    }

    @Test
    public void test_createStrategies_should_return_no_strategy() {
        CommandLine cmd = createCommandLine();
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).isEmpty();
    }

    @Test
    public void test_createStrategies_should_return_catalog_strategy() {
        CommandLine cmd = createCommandLine("-" + CommandLineService.OPTION_PARSER_CATALOG, "0");
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).hasSize(1);
        assertThat(parserStrategies).containsExactly(catalogStrategy);
    }

    @Test
    public void test_createStrategies_should_return_product_strategy() {
        CommandLine cmd = createCommandLine("-" + CommandLineService.OPTION_PARSER_PRODUCT, "0");
        parserStrategyFactory.parseCommandLine(cmd);

        List<ParserStrategy> parserStrategies = parserStrategyFactory.createStrategies();
        assertThat(parserStrategies).hasSize(1);
        assertThat(parserStrategies).containsExactly(productStrategy);
    }

    private CommandLine createCommandLine(String... args) {
        return cmdService.createCommandLine(cmdService.createOptions(), args);
    }
}
