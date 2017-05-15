package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.Manufacturer;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class ManufacturerXlsxConverter extends AbstractXlsxConverter<Manufacturer> {

    private final StringService stringService;

    @Autowired
    public ManufacturerXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "manufacturers";
    }

    void buildHeader(RowContext rowCtx) {
        rowCtx.
                header("Название").setColumnWidth(25).
                header("URL").setColumnWidth(55).
                header("Последнее изменение").setColumnWidth(20);
    }

    void writeRow(Manufacturer manufacturer, RowContext rowCtx) {
        rowCtx.
                text(stringService.defaultString(manufacturer.getName())).
                text(stringService.defaultString(manufacturer.getUrl())).
                text(manufacturer.getLastUpdate().toString());
    }
}
