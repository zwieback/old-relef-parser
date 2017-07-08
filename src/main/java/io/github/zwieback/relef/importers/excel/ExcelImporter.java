package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.importers.Importer;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ExcelImporter<T> extends Importer<T> {

    private static final Logger log = LogManager.getLogger(ExcelImporter.class);

    final ExcelCellReader excelCellReader;

    ExcelImporter(ExcelCellReader excelCellReader, String fileName) {
        super(fileName);
        this.excelCellReader = excelCellReader;
    }

    @SneakyThrows(IOException.class)
    @Override
    public List<T> doImport() {
        @Cleanup FileInputStream excelFile = new FileInputStream(buildFileName(getFileName()));
        Workbook workbook = new XSSFWorkbook(excelFile);
        log.info(String.format("Number of sheets = %d", workbook.getNumberOfSheets()));
        Sheet sheet = workbook.getSheetAt(getDataSheetNumber());
        log.info(String.format("Number of rows = %d", sheet.getLastRowNum()));
        return processRows(sheet);
    }

    abstract int getDataSheetNumber();

    abstract int skipNumberOfRows();

    private List<T> processRows(Sheet sheet) {
        List<T> resultList = new ArrayList<>(sheet.getLastRowNum());
        Iterator<Row> rowIterator = sheet.rowIterator();
        for (int row = 0; row < skipNumberOfRows() && rowIterator.hasNext(); row++) {
            rowIterator.next();
        }
        while (rowIterator.hasNext()) {
            T entity = processRow(rowIterator.next());
            if (entity != null) {
                resultList.add(entity);
            }
        }
        return resultList;
    }

    abstract T processRow(Row currentRow);
}
