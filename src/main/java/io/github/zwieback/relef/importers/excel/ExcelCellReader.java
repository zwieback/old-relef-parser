package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.services.StringService;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Component
public class ExcelCellReader {

    private final StringService stringService;

    @Autowired
    public ExcelCellReader(StringService stringService) {
        this.stringService = stringService;
    }

    @Nullable
    String readString(Cell currentCell) {
        if (!CellType.STRING.equals(currentCell.getCellTypeEnum())
                || StringUtils.isEmpty(currentCell.getStringCellValue())) {
            return null;
        }
        return currentCell.getStringCellValue();
    }

    @Nullable
    BigDecimal readPrice(Cell currentCell) {
        Double doubleValue = readDouble(currentCell);
        if (doubleValue == null) {
            return null;
        }
        return BigDecimal.valueOf(doubleValue);
    }

    @Nullable
    Double readDouble(Cell currentCell) {
        if (CellType.NUMERIC.equals(currentCell.getCellTypeEnum())) {
            return currentCell.getNumericCellValue();
        }
        if (CellType.STRING.equals(currentCell.getCellTypeEnum())) {
            return stringService.parseToDouble(currentCell.getStringCellValue());
        }
        return null;
    }

    @Nullable
    Boolean readBoolean(Cell currentCell) {
        if (CellType.BOOLEAN.equals(currentCell.getCellTypeEnum())) {
            return currentCell.getBooleanCellValue();
        }
        if (CellType.STRING.equals(currentCell.getCellTypeEnum())) {
            return stringService.parseToBoolean(currentCell.getStringCellValue());
        }
        return null;
    }

    @Nullable
    String readUrl(Cell cell) {
        if (readString(cell) == null) {
            return null;
        }
        Hyperlink hyperlink = cell.getHyperlink();
        if (hyperlink == null) {
            return null;
        }
        if (HyperlinkType.URL != hyperlink.getTypeEnum()) {
            return null;
        }
        return hyperlink.getAddress();
    }
}
