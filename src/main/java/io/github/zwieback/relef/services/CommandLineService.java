package io.github.zwieback.relef.services;

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
    public static final String OPTION_DOWNLOAD_PRODUCT_IMAGE = "dpi";

    private static final List<String> OPTIONS_PARSER = Arrays.asList(OPTION_PARSER_FULL, OPTION_PARSER_CATALOG,
            OPTION_PARSER_PRODUCT);
    private static final List<String> OPTIONS_EXPORT = Arrays.asList(OPTION_EXPORT_BRAND, OPTION_EXPORT_CATALOG,
            OPTION_EXPORT_PRODUCT, OPTION_EXPORT_MANUFACTURER, OPTION_EXPORT_TRADE_MARK);
    private static final List<String> OPTIONS_DOWNLOAD = Arrays.asList(OPTION_DOWNLOAD_PRODUCT_IMAGE);

    public Options createOptions() {
        Options options = new Options();
        buildParserOptions().forEach(options::addOption);
        buildExportOptions().forEach(options::addOption);
        buildDownloadOptions().forEach(options::addOption);
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
        return exportOptions;
    }

    private List<Option> buildDownloadOptions() {
        List<Option> downloadOptions = new ArrayList<>();
        downloadOptions.add(Option.builder(OPTION_DOWNLOAD_PRODUCT_IMAGE)
                .longOpt("download-product-image")
                .desc("download main image of all products")
                .build());
        return downloadOptions;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.cli.CommandLineParser#parse(Options, String[])
     */
    public CommandLine createCommandLine(Options options, String[] args) throws ParseException {
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
     * Does the command line contains any of the download options?
     *
     * @param cmd command line
     * @return {@code true} if {@code cmd} contains any of the download options, {@code false} otherwise
     */
    public boolean doesCommandLineContainsAnyDownloadOptions(CommandLine cmd) {
        return doesCommandLineContainsAnyOptions(OPTIONS_DOWNLOAD, cmd);
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
