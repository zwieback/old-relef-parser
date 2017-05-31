package io.github.zwieback.relef.importers;

import io.github.zwieback.relef.importers.excel.MsProductImporter;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_IMPORT_MY_SKLAD_PRODUCT;

@Service
public class ImporterFactory {

    private final MsProductImporter msProductImporter;

    @Autowired
    public ImporterFactory(MsProductImporter msProductImporter) {
        this.msProductImporter = msProductImporter;
    }

    /**
     * Determine and collect importers by options specified in the command line.
     *
     * @param cmd command line
     * @return list of importers specified in the command line
     * @throws IllegalArgumentException if no importer found
     */
    public List<Importer> determineImportersByCommandLine(CommandLine cmd) {
        List<Importer> importers = new ArrayList<>();
        if (cmd.hasOption(OPTION_IMPORT_MY_SKLAD_PRODUCT)) {
            msProductImporter.setFileName(cmd.getOptionValue(OPTION_IMPORT_MY_SKLAD_PRODUCT));
            importers.add(msProductImporter);
        }
        if (importers.isEmpty()) {
            throw new IllegalArgumentException("No importer found");
        }
        return importers;
    }
}
