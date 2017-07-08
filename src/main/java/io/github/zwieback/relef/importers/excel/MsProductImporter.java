package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsProductImporter extends ExcelImporter<MsProductDto> {

    @Autowired
    public MsProductImporter(ExcelCellReader excelCellReader, String fileName) {
        super(excelCellReader, fileName);
    }

    @Override
    int getDataSheetNumber() {
        return 0;
    }

    @Override
    int skipNumberOfRows() {
        return 1;
    }

    @Override
    MsProductDto processRow(Row currentRow) {
        MsProductDto result = new MsProductDto();
        int cellNumber = 0;
        for (Cell currentCell : currentRow) {
            if (CellType.BLANK.equals(currentCell.getCellTypeEnum())) {
                cellNumber++;
                continue;
            }
            switch (cellNumber) {
                case 0:
                    // Группы
                    result.setCatalogName(excelCellReader.readString(currentCell));
                    break;
                case 1:
                    // Код
                    result.setCode(excelCellReader.readString(currentCell));
                    break;
                case 2:
                    // Наименование
                    result.setName(excelCellReader.readString(currentCell));
                    break;
                case 3:
                    // Внешний код
                    result.setExternalCode(excelCellReader.readString(currentCell));
                    break;
                case 4:
                    // Артикул
                    result.setArticle(excelCellReader.readString(currentCell));
                    break;
                case 5:
                    // Единица измерения
                    result.setUnit(excelCellReader.readString(currentCell));
                    break;
                case 6:
                    // Цена продажи
                    result.setSellingPrice(excelCellReader.readPrice(currentCell));
                    break;
                case 7:
                    // Валюта (Цена продажи)
                    break;
                case 8:
                    // Старая Цена
                    result.setOldPrice(excelCellReader.readPrice(currentCell));
                    break;
                case 9:
                    // Валюта (Старая Цена)
                    break;
                case 10:
                    // Новая Цена
                    result.setNewPrice(excelCellReader.readPrice(currentCell));
                    break;
                case 11:
                    // Валюта (Новая Цена)
                    break;
                case 12:
                    // Закупочная цена
                    result.setPurchasePrice(excelCellReader.readPrice(currentCell));
                    break;
                case 13:
                    // Валюта (Закупочная цена)
                    break;
                case 14:
                    // Неснижаемый остаток
                    break;
                case 15:
                    // Штрихкод EAN13
                    result.setBarcodeEan13(excelCellReader.readString(currentCell));
                    break;
                case 16:
                    // Штрихкод EAN8
                    result.setBarcodeEan8(excelCellReader.readString(currentCell));
                    break;
                case 17:
                    // Штрихкод Code128
                    result.setBarcodeCode128(excelCellReader.readString(currentCell));
                    break;
                case 18:
                    // Описание
                    result.setDescription(excelCellReader.readString(currentCell));
                    break;
                case 19:
                    // Минимальная цена
                    result.setMinPrice(excelCellReader.readPrice(currentCell));
                    break;
                case 20:
                    // Валюта (Минимальная цена)
                    break;
                case 21:
                    // Страна
                    result.setManufacturerCountry(excelCellReader.readString(currentCell));
                    break;
                case 22:
                    // НДС
                    break;
                case 23:
                    // Поставщик
                    result.setProvider(excelCellReader.readString(currentCell));
                    break;
                case 24:
                    // Архивный
                    result.setArchival(excelCellReader.readBoolean(currentCell));
                    break;
                case 25:
                    // Вес
                    result.setWeight(excelCellReader.readDouble(currentCell));
                    break;
                case 26:
                    // Объем
                    result.setVolume(excelCellReader.readDouble(currentCell));
                    break;
                case 27:
                    // Код модификации
                    result.setModificationCode(excelCellReader.readString(currentCell));
                    break;
                case 28:
                    // Производитель
                    result.setManufacturer(excelCellReader.readString(currentCell));
                    break;
                default:
                    // Unaccounted field
                    break;
            }
            cellNumber++;
        }
        return result;
    }
}
