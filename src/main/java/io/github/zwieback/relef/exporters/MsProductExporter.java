package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import io.github.zwieback.relef.exporters.xlsx.MsProductXlsxConverter;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsProductExporter extends Exporter {

    private final MsProductXlsxConverter msProductXlsxConverter;
    private final ProductRepository productRepository;

    @Autowired
    public MsProductExporter(DateTimeService dateTimeService,
                             MsProductXlsxConverter msProductXlsxConverter,
                             ProductRepository productRepository) {
        super(dateTimeService);
        this.msProductXlsxConverter = msProductXlsxConverter;
        this.productRepository = productRepository;
    }

    private List<MsProductDto> findAllMsProducts() {
        return productRepository.findAllMsProducts();
    }

    @Override
    public byte[] toXlsx() {
        return msProductXlsxConverter.toXlsx(findAllMsProducts());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("ms_products_", EXTENSION_XLSX);
    }
}
