package io.github.zwieback.relef.services;

import org.apache.commons.cli.*;
import org.springframework.stereotype.Service;

@Service
public class CommandLineService {

    public static final String OPTION_HELP = "h";
    public static final String OPTION_CATALOG_IDS = "ci";
    public static final String OPTION_PRODUCT_IDS = "pi";

    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder(OPTION_CATALOG_IDS)
                .longOpt("catalog-ids")
                .desc("parse only this catalog ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        options.addOption(Option.builder(OPTION_PRODUCT_IDS)
                .longOpt("product-ids")
                .desc("parse only this product ids")
                .hasArg()
                .argName("id1,id2,...,idN")
                .build());
        options.addOption(OPTION_HELP, "help", false, "print this message");
        return options;
    }

    public CommandLine createCommandLine(Options options, String[] args) throws ParseException {
        CommandLineParser cmdParser = new DefaultParser();
        return cmdParser.parse(options, args);
    }

    public void printHelp(Options options) {
        String footer = "\nPlease report issues at https://github.com/zwieback/relef-parser/issues";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java", null, options, footer, true);
    }
}
