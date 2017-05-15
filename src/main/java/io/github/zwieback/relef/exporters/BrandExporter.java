package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.exporters.xlsx.BrandXlsxConverter;
import io.github.zwieback.relef.repositories.BrandRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandExporter extends Exporter {

    private final BrandXlsxConverter brandXlsxConverter;
    private final BrandRepository brandRepository;

    @Autowired
    public BrandExporter(DateTimeService dateTimeService,
                         BrandXlsxConverter brandXlsxConverter,
                         BrandRepository brandRepository) {
        super(dateTimeService);
        this.brandXlsxConverter = brandXlsxConverter;
        this.brandRepository = brandRepository;
    }

    private List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public byte[] toXlsx() {
        return brandXlsxConverter.toXlsx(findAllBrands());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("brands_", EXTENSION_XLSX);
    }
}
