package io.github.zwieback.relef;

import io.github.zwieback.relef.configs.ApplicationConfig;
import io.github.zwieback.relef.exporters.Exporter;
import io.github.zwieback.relef.exporters.ExporterFactory;
import io.github.zwieback.relef.parsers.strategies.ParserStrategy;
import io.github.zwieback.relef.parsers.strategies.ParserStrategyFactory;
import io.github.zwieback.relef.services.CommandLineService;
import io.github.zwieback.relef.services.FileService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws ParseException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        CommandLineService cmdService = context.getBean(CommandLineService.class);
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, args);
        if (cmdService.hasCommandLineParserOption(cmd)) {
            parse(context, cmd);
        } else if (cmdService.hasCommandLineExportOption(cmd)) {
            export(context, cmd);
        } else {
            cmdService.printHelp(options);
        }
        context.close();
    }

    private static void parse(AbstractApplicationContext context, CommandLine cmd) {
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
            log.info("Parsing completed successfully");
        } catch (Exception e) {
            log.error("Parsing completed with error: " + e.getMessage(), e);
        }
    }

    private static void export(AbstractApplicationContext context, CommandLine cmd) {
        log.info("Export started...");
        try {
            FileService fileService = context.getBean(FileService.class);
            ExporterFactory exporterFactory = context.getBean(ExporterFactory.class);
            Exporter exporter = exporterFactory.determineExporterByCommandLine(cmd);
            fileService.writeBytes(exporter.toXlsx(), exporter.getXlsxFileName());
            log.info("Export completed successfully");
        } catch (Exception e) {
            log.error("Export completed with error: " + e.getMessage(), e);
        }
    }
}
