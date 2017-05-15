package io.github.zwieback.relef.exporters.xlsx;

import org.subtlelib.poi.api.row.RowContext;
import org.subtlelib.poi.api.sheet.SheetContext;
import org.subtlelib.poi.api.workbook.WorkbookContext;
import org.subtlelib.poi.impl.workbook.WorkbookContextFactory;

import java.util.List;

public abstract class AbstractXlsxConverter<T> {

    public byte[] toXlsx(List<T> entities) {
        WorkbookContext workbookCtx = WorkbookContextFactory.useXlsx().createWorkbook();
        SheetContext sheetCtx = workbookCtx.createSheet(getSheetName());
        buildHeader(sheetCtx.nextRow());
        for (T entity : entities) {
            RowContext rowContext = sheetCtx.nextRow();
            writeRow(entity, rowContext);
        }
        return workbookCtx.toNativeBytes();
    }

    abstract String getSheetName();

    abstract void buildHeader(RowContext rowCtx);

    abstract void writeRow(T entity, RowContext rowCtx);
}
