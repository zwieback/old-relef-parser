package io.github.zwieback.relef;

import io.github.zwieback.relef.analyzers.Analyzer;
import io.github.zwieback.relef.analyzers.AnalyzerFactory;
import io.github.zwieback.relef.configs.ApplicationConfig;
import io.github.zwieback.relef.downloaders.Downloader;
import io.github.zwieback.relef.downloaders.DownloaderFactory;
import io.github.zwieback.relef.exporters.Exporter;
import io.github.zwieback.relef.exporters.ExporterFactory;
import io.github.zwieback.relef.parser.strategies.ParserStrategy;
import io.github.zwieback.relef.parser.strategies.ParserStrategyFactory;
import io.github.zwieback.relef.services.CommandLineService;
import io.github.zwieback.relef.services.FileService;
import io.github.zwieback.relef.web.services.AuthService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;

public class Application {

    private static final Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        CommandLineService cmdService = context.getBean(CommandLineService.class);
        Options options = cmdService.createOptions();
        CommandLine cmd = cmdService.createCommandLine(options, args);
        if (cmdService.doesCommandLineContainsAnyParserOptions(cmd)) {
            parse(context, cmd);
        }
        if (cmdService.doesCommandLineContainsAnyExportOptions(cmd)) {
            export(context, cmd);
        }
        if (cmdService.doesCommandLineContainsAnyDownloadOptions(cmd)) {
            download(context, cmd);
        }
        if (cmdService.doesCommandLineContainsAnyAnalyzeOptions(cmd)) {
            analyze(context, cmd);
        }
        if (cmdService.doesCommandLineContainsHelpOption(cmd)) {
            cmdService.printHelp(options);
        }
        context.close();
    }

    private static void parse(AbstractApplicationContext context, CommandLine cmd) {
        log.info("Parsing started...");
        try {
            AuthService authService = context.getBean(AuthService.class);
            authService.auth();
            ParserStrategyFactory strategyFactory = context.getBean(ParserStrategyFactory.class);
            strategyFactory.parseCommandLine(cmd);
            List<ParserStrategy> strategies = strategyFactory.createStrategies();
            strategies.forEach(strategy -> {
                log.info("Started " + strategy.getClass().getSimpleName() + " strategy");
                strategy.parse();
            });
            authService.logout();
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
            List<Exporter> exporters = exporterFactory.determineExportersByCommandLine(cmd);
            exporters.forEach(exporter -> {
                log.info("Started " + exporter.getClass().getSimpleName() + " export");
                fileService.writeBytes(exporter.toXlsx(), exporter.getXlsxFileName());
            });
            log.info("Export completed successfully");
        } catch (Exception e) {
            log.error("Export completed with error: " + e.getMessage(), e);
        }
    }

    private static void download(AbstractApplicationContext context, CommandLine cmd) {
        log.info("Download started...");
        try {
            DownloaderFactory downloaderFactory = context.getBean(DownloaderFactory.class);
            List<Downloader<?>> downloaders = downloaderFactory.determineDownloadersByCommandLine(cmd);
            downloaders.forEach(downloader -> {
                log.info("Started " + downloader.getClass().getSimpleName() + " download");
                downloader.download();
            });
            log.info("Download completed successfully");
        } catch (Exception e) {
            log.error("Download completed with error: " + e.getMessage(), e);
        }
    }

    private static void analyze(AbstractApplicationContext context, CommandLine cmd) {
        log.info("Analyze started...");
        try {
            AnalyzerFactory analyzerFactory = context.getBean(AnalyzerFactory.class);
            List<Analyzer> analyzers = analyzerFactory.determineImportersByCommandLine(cmd);
            analyzers.forEach(analyzer -> {
                log.info("Started " + analyzer.getClass().getSimpleName() + " analyze");
                analyzer.analyze();
            });
            log.info("Analyze completed successfully");
        } catch (Exception e) {
            log.error("Analyze completed with error: " + e.getMessage(), e);
        }
    }
}
