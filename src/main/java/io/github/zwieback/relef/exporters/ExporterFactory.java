package io.github.zwieback.relef.exporters;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_BRAND;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_CATALOG;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_MANUFACTURER;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_PRODUCT;
import static io.github.zwieback.relef.services.CommandLineService.OPTION_EXPORT_TRADE_MARK;

@Service
public class ExporterFactory {

    private final BrandExporter brandExporter;
    private final CatalogExporter catalogExporter;
    private final ProductExporter productExporter;
    private final ManufacturerExporter manufacturerExporter;
    private final TradeMarkExporter tradeMarkExporter;

    @Autowired
    public ExporterFactory(BrandExporter brandExporter,
                           CatalogExporter catalogExporter,
                           ProductExporter productExporter,
                           ManufacturerExporter manufacturerExporter,
                           TradeMarkExporter tradeMarkExporter) {
        this.brandExporter = brandExporter;
        this.catalogExporter = catalogExporter;
        this.productExporter = productExporter;
        this.manufacturerExporter = manufacturerExporter;
        this.tradeMarkExporter = tradeMarkExporter;
    }

    /**
     * Determine and collect exporters by options specified in the command line.
     *
     * @param cmd command line
     * @return list of exporters specified in the command line
     * @throws IllegalArgumentException if no exporter found
     */
    public List<Exporter> determineExportersByCommandLine(CommandLine cmd) {
        List<Exporter> exporters = new ArrayList<>();
        if (cmd.hasOption(OPTION_EXPORT_BRAND)) {
            exporters.add(brandExporter);
        }
        if (cmd.hasOption(OPTION_EXPORT_CATALOG)) {
            exporters.add(catalogExporter);
        }
        if (cmd.hasOption(OPTION_EXPORT_PRODUCT)) {
            exporters.add(productExporter);
        }
        if (cmd.hasOption(OPTION_EXPORT_MANUFACTURER)) {
            exporters.add(manufacturerExporter);
        }
        if (cmd.hasOption(OPTION_EXPORT_TRADE_MARK)) {
            exporters.add(tradeMarkExporter);
        }
        if (exporters.isEmpty()) {
            throw new IllegalArgumentException("No exporter found");
        }
        return exporters;
    }
}
