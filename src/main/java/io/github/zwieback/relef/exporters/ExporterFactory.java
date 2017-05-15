package io.github.zwieback.relef.exporters;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Exporter determineExporterByCommandLine(CommandLine cmd) {
        if (cmd.hasOption(OPTION_EXPORT_BRAND)) {
            return brandExporter;
        }
        if (cmd.hasOption(OPTION_EXPORT_CATALOG)) {
            return catalogExporter;
        }
        if (cmd.hasOption(OPTION_EXPORT_PRODUCT)) {
            return productExporter;
        }
        if (cmd.hasOption(OPTION_EXPORT_MANUFACTURER)) {
            return manufacturerExporter;
        }
        if (cmd.hasOption(OPTION_EXPORT_TRADE_MARK)) {
            return tradeMarkExporter;
        }
        throw new IllegalArgumentException("No exporter found");
    }
}
