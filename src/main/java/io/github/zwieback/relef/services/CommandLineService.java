package io.github.zwieback.relef.services;

import lombok.SneakyThrows;
import org.apache.commons.cli.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommandLineService {

    public static final String OPTION_HELP = "h";
    public static final String OPTION_PARSER_FULL = "pf";
    public static final String OPTION_PARSER_CATALOG = "pc";
    public static final String OPTION_PARSER_PRODUCT = "pp";
    public static final String OPTION_EXPORT_BRAND = "eb";
    public static final String OPTION_EXPORT_CATALOG = "ec";
    public static final String OPTION_EXPORT_PRODUCT = "ep";
    public static final String OPTION_EXPORT_MANUFACTURER = "em";
    public static final String OPTION_EXPORT_TRADE_MARK = "etm";
    public static final String OPTION_EXPORT_MS_PRODUCT = "emsp";
    public static final String OPTION_IMPORT_MY_SKLAD_PRODUCT = "imsp";
    public static final String OPTION_IMPORT_SAMSON_PRODUCT = "isp";
    public static final String OPTION_DOWNLOAD_PRODUCT_IMAGE = "dpi";
    public static final String OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE = "dspi";
    public static final String OPTION_ANALYZE_MY_SKLAD_PRODUCT = "amsp";

    private static final List<String> OPTIONS_PARSER = Arrays.asList(OPTION_PARSER_FULL, OPTION_PARSER_CATALOG,
            OPTION_PARSER_PRODUCT);
    private static final List<String> OPTIONS_EXPORT = Arrays.asList(OPTION_EXPORT_BRAND, OPTION_EXPORT_CATALOG,
            OPTION_EXPORT_PRODUCT, OPTION_EXPORT_MANUFACTURER, OPTION_EXPORT_TRADE_MARK, OPTION_EXPORT_MS_PRODUCT);
    private static final List<String> OPTIONS_IMPORT = Arrays.asList(OPTION_IMPORT_MY_SKLAD_PRODUCT,
            OPTION_IMPORT_SAMSON_PRODUCT);
    private static final List<String> OPTIONS_DOWNLOAD = Arrays.asList(OPTION_DOWNLOAD_PRODUCT_IMAGE,
            OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE);
    private static final List<String> OPTIONS_ANALYZE = Arrays.asList(OPTION_ANALYZE_MY_SKLAD_PRODUCT);

    public Options createOptions() {
        Options options = new Options();
        buildParserOptions().forEach(options::addOption);
        buildExportOptions().forEach(options::addOption);
        buildImportOptions().forEach(options::addOption);
        buildDownloadOptions().forEach(options::addOption);
        buildAnalyzeOptions().forEach(options::addOption);
        options.addOption(OPTION_HELP, "help", false, "print this message");
        return options;
    }

    private List<Option> buildParserOptions() {
        List<Option> parserOptions = new ArrayList<>();
        parserOptions.add(Option.builder(OPTION_PARSER_FULL)
                .longOpt("parser-full")
                .desc("parse all products on site")
                .hasArg()
                .argName("0 - fast parsing (no description and not all properties, default)\n" +
                        "1 - slow parsing (every field of product)\n" +
                        "2 - hybrid parsing (fast plus slow if product does not exists or has no description)")
                .build());
        parserOptions.add(Option.builder(OPTION_PARSER_CATALOG)
                .longOpt("parser-catalog")
                .desc("parse only this catalog ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        parserOptions.add(Option.builder(OPTION_PARSER_PRODUCT)
                .longOpt("parser-product")
                .desc("parse only this product ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        return parserOptions;
    }

    private List<Option> buildExportOptions() {
        List<Option> exportOptions = new ArrayList<>();
        exportOptions.add(Option.builder(OPTION_EXPORT_BRAND)
                .longOpt("export-brand")
                .desc("export all brands")
                .build());
        exportOptions.add(Option.builder(OPTION_EXPORT_CATALOG)
                .longOpt("export-catalog")
                .desc("export all catalogs")
                .build());
        exportOptions.add(Option.builder(OPTION_EXPORT_PRODUCT)
                .longOpt("export-product")
                .desc("export all products")
                .build());
        exportOptions.add(Option.builder(OPTION_EXPORT_MANUFACTURER)
                .longOpt("export-manufacturer")
                .desc("export all manufacturers")
                .build());
        exportOptions.add(Option.builder(OPTION_EXPORT_TRADE_MARK)
                .longOpt("export-trade-mark")
                .desc("export all trade marks")
                .build());
        exportOptions.add(Option.builder(OPTION_EXPORT_MS_PRODUCT)
                .longOpt("export-my-sklad-product")
                .desc("export all products of MySklad system")
                .build());
        return exportOptions;
    }

    private List<Option> buildImportOptions() {
        List<Option> importOptions = new ArrayList<>();
        importOptions.add(Option.builder(OPTION_IMPORT_MY_SKLAD_PRODUCT)
                .longOpt("import-my-sklad-product")
                .desc("import products of MySklad system from file")
                .hasArg()
                .argName("fileName")
                .build());
        importOptions.add(Option.builder(OPTION_IMPORT_SAMSON_PRODUCT)
                .longOpt("import-samson-product")
                .desc("import products of Samson system from file")
                .hasArg()
                .argName("fileName")
                .build());
        return importOptions;
    }

    private List<Option> buildDownloadOptions() {
        List<Option> downloadOptions = new ArrayList<>();
        downloadOptions.add(Option.builder(OPTION_DOWNLOAD_PRODUCT_IMAGE)
                .longOpt("download-product-image")
                .desc("download main image of all products")
                .build());
        downloadOptions.add(Option.builder(OPTION_DOWNLOAD_SAMSON_PRODUCT_IMAGE)
                .longOpt("download-samson-product-image")
                .desc("download main image of all products from Samson system")
                .hasArg()
                .argName("fileName")
                .build());
        return downloadOptions;
    }

    private List<Option> buildAnalyzeOptions() {
        List<Option> analyzeOptions = new ArrayList<>();
        analyzeOptions.add(Option.builder(OPTION_ANALYZE_MY_SKLAD_PRODUCT)
                .longOpt("analyze-my-sklad-product")
                .desc("analyze products of MySklad system from file")
                .hasArg()
                .argName("fileName")
                .build());
        return analyzeOptions;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.cli.CommandLineParser#parse(Options, String[])
     */
    @SneakyThrows(ParseException.class)
    public CommandLine createCommandLine(Options options, String[] args) {
        CommandLineParser cmdParser = new DefaultParser();
        return cmdParser.parse(options, args);
    }

    /**
     * Print help to console.
     *
     * @param options options to print
     */
    public void printHelp(Options options) {
        String footer = "\nPlease report issues at https://github.com/zwieback/relef-parser/issues";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar relef-parser-{version}.jar [command] <command options>", null, options, footer);
    }

    /**
     * Does the command line contains any of the parser options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the parser options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyParserOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_PARSER, cmd);
    }

    /**
     * Does the command line contains any of the export options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the export options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyExportOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_EXPORT, cmd);
    }

    /**
     * Does the command line contains any of the import options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the import options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyImportOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_IMPORT, cmd);
    }

    /**
     * Does the command line contains any of the download options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the download options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyDownloadOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_DOWNLOAD, cmd);
    }

    /**
     * Does the command line contains any of the analyze options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the analyze options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyAnalyzeOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_ANALYZE, cmd);
    }

    private static boolean doesCommandLineContainsAnyOptions(List<String> options, CommandLine cmd) {
        return options.stream().anyMatch(cmd::hasOption);
    }

    /**
     * Does the command line contains a help option?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} have no options or contains a help option, {@code false} otherwise
     */
    public boolean doesCommandLineContainsHelpOption(CommandLine cmd) {
        return cmd.getOptions().length == 0 || cmd.hasOption(OPTION_HELP);
    }
}
