package io.github.zwieback.relef.analyzers;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_ANALYZE_MY_SKLAD_PRODUCT;

@Service
public class AnalyzerFactory {

    private final Function<String, MsProductAnalyzer> msProductAnalyzerFactory;

    @Autowired
    public AnalyzerFactory(Function<String, MsProductAnalyzer> msProductAnalyzerFactory) {
        this.msProductAnalyzerFactory = msProductAnalyzerFactory;
    }

    /**
     * Determine and collect analyzers by options specified in the command line.
     *
     * @param cmd command line
     * @return list of importers specified in the command line
     * @throws IllegalArgumentException if no importer found
     */
    public List<Analyzer> determineImportersByCommandLine(CommandLine cmd) {
        List<Analyzer> analyzers = new ArrayList<>();
        if (cmd.hasOption(OPTION_ANALYZE_MY_SKLAD_PRODUCT)) {
            String fileName = cmd.getOptionValue(OPTION_ANALYZE_MY_SKLAD_PRODUCT);
            analyzers.add(msProductAnalyzerFactory.apply(fileName));
        }
        if (analyzers.isEmpty()) {
            throw new IllegalArgumentException("No analyzer found");
        }
        return analyzers;
    }
}
