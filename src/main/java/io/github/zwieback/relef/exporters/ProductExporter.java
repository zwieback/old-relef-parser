package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.exporters.xlsx.ProductXlsxConverter;
import io.github.zwieback.relef.repositories.ProductRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductExporter extends Exporter {

    private final ProductXlsxConverter productXlsxConverter;
    private final ProductRepository productRepository;

    @Autowired
    public ProductExporter(DateTimeService dateTimeService,
                           ProductXlsxConverter productXlsxConverter,
                           ProductRepository productRepository) {
        super(dateTimeService);
        this.productXlsxConverter = productXlsxConverter;
        this.productRepository = productRepository;
    }

    private List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public byte[] toXlsx() {
        return productXlsxConverter.toXlsx(findAllProducts());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("products_", EXTENSION_XLSX);
    }
}
