package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.importers.Importer;
import io.github.zwieback.relef.services.StringService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ExcelImporter<T> extends Importer<T> {

    private static final Logger log = LogManager.getLogger(ExcelImporter.class);

    private final StringService stringService;

    ExcelImporter(StringService stringService, String fileName) {
        super(fileName);
        this.stringService = stringService;
    }

    @Override
    public List<T> doImport() {
        try (FileInputStream excelFile = new FileInputStream(buildFileName(getFileName()))) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            log.info(String.format("Number of sheets = %d", workbook.getNumberOfSheets()));
            Sheet sheet = workbook.getSheetAt(getDataSheetNumber());
            log.info(String.format("Number of rows = %d", sheet.getLastRowNum()));
            return processRows(sheet);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UncheckedIOException(e.getMessage(), e);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    abstract int getDataSheetNumber();

    abstract int skipNumberOfRows();

    private List<T> processRows(Sheet sheet) throws ParseException {
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

    abstract T processRow(Row currentRow) throws ParseException;

    @Nullable
    static String getStringValue(Cell currentCell) {
        if (!CellType.STRING.equals(currentCell.getCellTypeEnum())
                || StringUtils.isEmpty(currentCell.getStringCellValue())) {
            return null;
        }
        return currentCell.getStringCellValue();
    }

    @Nullable
    BigDecimal getPriceValue(Cell currentCell) throws ParseException {
        Double doubleValue = getDoubleValue(currentCell);
        if (doubleValue == null) {
            return null;
        }
        return BigDecimal.valueOf(doubleValue);
    }

    @Nullable
    Double getDoubleValue(Cell currentCell) throws ParseException {
        if (CellType.NUMERIC.equals(currentCell.getCellTypeEnum())) {
            return currentCell.getNumericCellValue();
        }
        if (CellType.STRING.equals(currentCell.getCellTypeEnum())) {
            return stringService.parseToDouble(currentCell.getStringCellValue());
        }
        return null;
    }

    @Nullable
    Boolean getBooleanValue(Cell currentCell) {
        if (CellType.BOOLEAN.equals(currentCell.getCellTypeEnum())) {
            return currentCell.getBooleanCellValue();
        }
        if (CellType.STRING.equals(currentCell.getCellTypeEnum())) {
            return stringService.parseToBoolean(currentCell.getStringCellValue());
        }
        return null;
    }
}
