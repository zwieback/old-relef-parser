package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.exporters.xlsx.CatalogXlsxConverter;
import io.github.zwieback.relef.repositories.CatalogRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogExporter extends Exporter {

    private final CatalogXlsxConverter catalogXlsxConverter;
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogExporter(DateTimeService dateTimeService,
                           CatalogXlsxConverter catalogXlsxConverter,
                           CatalogRepository catalogRepository) {
        super(dateTimeService);
        this.catalogXlsxConverter = catalogXlsxConverter;
        this.catalogRepository = catalogRepository;
    }

    private List<Catalog> findAllCatalogs() {
        return catalogRepository.findAll();
    }

    @Override
    public byte[] toXlsx() {
        return catalogXlsxConverter.toXlsx(findAllCatalogs());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("catalogs_", getXlsxExtension());
    }
}
