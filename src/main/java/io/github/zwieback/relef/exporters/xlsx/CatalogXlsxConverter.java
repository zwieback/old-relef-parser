package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.Catalog;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class CatalogXlsxConverter extends AbstractXlsxConverter<Catalog> {

    private final StringService stringService;

    @Autowired
    public CatalogXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "catalogs";
    }

    @Override
    void buildHeader(RowContext rowCtx) {
        rowCtx.
                header("ID").setColumnWidth(10).
                header("ID родителя").setColumnWidth(10).
                header("URL").setColumnWidth(25).
                header("Название").setColumnWidth(30).
                header("Уровень").setColumnWidth(10).
                header("Последнее изменение").setColumnWidth(20);
    }

    @Override
    void writeRow(Catalog catalog, RowContext rowCtx) {
        rowCtx.
                number(catalog.getId()).
                text(stringService.defaultString(catalog.getParentId())).
                text(stringService.defaultString(catalog.getUrl())).
                text(stringService.defaultString(catalog.getName())).
                text(catalog.getLevel().toString()).
                text(catalog.getLastUpdate().toString());
    }
}
