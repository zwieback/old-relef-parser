package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.entities.dto.samson.SamsonProductDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SamsonProductImporter extends ExcelImporter<SamsonProductDto> {

    private static final int MAX_CELL_NUMBER = 2;

    private String lastCatalog = "undefined";

    @Autowired
    public SamsonProductImporter(ExcelCellReader excelCellReader, String fileName) {
        super(excelCellReader, fileName);
    }

    @Override
    int getDataSheetNumber() {
        return 6;
    }

    @Override
    int skipNumberOfRows() {
        return 10;
    }

    @Override
    SamsonProductDto processRow(Row currentRow) {
        SamsonProductDto result = new SamsonProductDto();
        int cellNumber = 0;
        for (Cell currentCell : currentRow) {
            switch (cellNumber) {
                case 1:
                    // Наименование
                    String name = excelCellReader.readString(currentCell);
                    if (StringUtils.isEmpty(name)) {
                        return null;
                    }
                    result.setName(name);
                    break;
                case 2:
                    // Ссылка на изображение
                    String photoUrl = excelCellReader.readUrl(currentCell);
                    if (StringUtils.isEmpty(photoUrl)) {
                        // in this case, the catalog doesn't have a photo URL,
                        // so the name is the name of the catalog, not the entity
                        lastCatalog = result.getName();
                        return null;
                    }
                    result.setPhotoUrl(photoUrl);
                    break;
                default:
                    // Unaccounted field
                    break;
            }
            // fields with number > MAX_CELL_NUMBER is not required in this case
            if (++cellNumber > MAX_CELL_NUMBER) {
                break;
            }
        }
        result.setCatalog(lastCatalog);
        return result;
    }
}
