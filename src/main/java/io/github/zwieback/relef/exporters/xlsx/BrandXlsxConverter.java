package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.Brand;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class BrandXlsxConverter extends AbstractXlsxConverter<Brand> {

    private final StringService stringService;

    @Autowired
    public BrandXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "brands";
    }

    @Override
    void buildHeader(RowContext rowCtx) {
        rowCtx.
                header("ID").setColumnWidth(10).
                header("Название").setColumnWidth(20).
                header("URL").setColumnWidth(25).
                header("URL изображения").setColumnWidth(50).
                header("Последнее изменение").setColumnWidth(20);
    }

    @Override
    void writeRow(Brand brand, RowContext rowCtx) {
        rowCtx.
                number(brand.getId()).
                text(stringService.defaultString(brand.getName())).
                text(stringService.defaultString(brand.getUrl())).
                text(stringService.defaultString(brand.getImageUrl())).
                text(brand.getLastUpdate().toString());
    }
}
