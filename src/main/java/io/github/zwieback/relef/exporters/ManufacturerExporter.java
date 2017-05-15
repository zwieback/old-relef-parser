package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.Manufacturer;
import io.github.zwieback.relef.exporters.xlsx.ManufacturerXlsxConverter;
import io.github.zwieback.relef.repositories.ManufacturerRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerExporter extends Exporter {

    private final ManufacturerXlsxConverter manufacturerXlsxConverter;
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerExporter(DateTimeService dateTimeService,
                                ManufacturerXlsxConverter manufacturerXlsxConverter,
                                ManufacturerRepository manufacturerRepository) {
        super(dateTimeService);
        this.manufacturerXlsxConverter = manufacturerXlsxConverter;
        this.manufacturerRepository = manufacturerRepository;
    }

    private List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public byte[] toXlsx() {
        return manufacturerXlsxConverter.toXlsx(findAllManufacturers());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("manufacturers_", EXTENSION_XLSX);
    }
}
