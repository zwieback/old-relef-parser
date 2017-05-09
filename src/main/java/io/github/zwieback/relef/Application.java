package io.github.zwieback.relef;

import io.github.zwieback.relef.configs.ApplicationConfig;
import io.github.zwieback.relef.parsers.strategies.ParserStrategy;
import io.github.zwieback.relef.parsers.strategies.ParserStrategyFactory;
import io.github.zwieback.relef.services.CommandLineService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_HELP;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws ParseException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        CommandLineService cmdService = context.getBean(CommandLineService.class);
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, args);
        if (cmd.hasOption(OPTION_HELP)) {
            cmdService.printHelp(options);
        } else {
            log.info("Parsing started...");
            try {
                ParserStrategyFactory strategyFactory = context.getBean(ParserStrategyFactory.class);
                strategyFactory.parseCommandLine(cmd);
                List<ParserStrategy> strategies = strategyFactory.createStrategies();
                strategies.forEach(strategy -> {
                    log.debug("Started " + strategy.getClass().getSimpleName() + " strategy");
                    strategy.parse();
                });

//                ParseRunner parseRunner = context.getBean(ParseRunner.class);
//                parseRunner.parse();
            } catch (Exception e) {
                log.error("Parsing completed with error: " + e.getMessage(), e);
                return;
            } finally {
                context.close();
            }
            log.info("Parsing completed successfully");
        }
    }
}
