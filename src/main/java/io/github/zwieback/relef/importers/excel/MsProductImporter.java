package io.github.zwieback.relef.importers.excel;

import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import io.github.zwieback.relef.services.StringService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class MsProductImporter extends ExcelImporter<MsProductDto> {

    @Autowired
    public MsProductImporter(StringService stringService) {
        super(stringService);
    }

    @Override
    MsProductDto processRow(Row currentRow) throws ParseException {
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
                    result.setCatalogName(getStringValue(currentCell));
                    break;
                case 1:
                    // Код
                    result.setCode(getStringValue(currentCell));
                    break;
                case 2:
                    // Наименование
                    result.setName(getStringValue(currentCell));
                    break;
                case 3:
                    // Внешний код
                    result.setExternalCode(getStringValue(currentCell));
                    break;
                case 4:
                    // Артикул
                    result.setArticle(getStringValue(currentCell));
                    break;
                case 5:
                    // Единица измерения
                    result.setUnit(getStringValue(currentCell));
                    break;
                case 6:
                    // Цена продажи
                    result.setSellingPrice(getPriceValue(currentCell));
                    break;
                case 7:
                    // Валюта (Цена продажи)
                    break;
                case 8:
                    // Старая Цена
                    result.setOldPrice(getPriceValue(currentCell));
                    break;
                case 9:
                    // Валюта (Старая Цена)
                    break;
                case 10:
                    // Новая Цена
                    result.setNewPrice(getPriceValue(currentCell));
                    break;
                case 11:
                    // Валюта (Новая Цена)
                    break;
                case 12:
                    // Закупочная цена
                    result.setPurchasePrice(getPriceValue(currentCell));
                    break;
                case 13:
                    // Валюта (Закупочная цена)
                    break;
                case 14:
                    // Неснижаемый остаток
                    break;
                case 15:
                    // Штрихкод EAN13
                    result.setBarcodeEan13(getStringValue(currentCell));
                    break;
                case 16:
                    // Штрихкод EAN8
                    result.setBarcodeEan8(getStringValue(currentCell));
                    break;
                case 17:
                    // Штрихкод Code128
                    result.setBarcodeCode128(getStringValue(currentCell));
                    break;
                case 18:
                    // Описание
                    result.setDescription(getStringValue(currentCell));
                    break;
                case 19:
                    // Минимальная цена
                    result.setMinPrice(getPriceValue(currentCell));
                    break;
                case 20:
                    // Валюта (Минимальная цена)
                    break;
                case 21:
                    // Страна
                    result.setManufacturerCountry(getStringValue(currentCell));
                    break;
                case 22:
                    // НДС
                    break;
                case 23:
                    // Поставщик
                    result.setProvider(getStringValue(currentCell));
                    break;
                case 24:
                    // Архивный
                    result.setArchival(getBooleanValue(currentCell));
                    break;
                case 25:
                    // Вес
                    result.setWeight(getDoubleValue(currentCell));
                    break;
                case 26:
                    // Объем
                    result.setVolume(getDoubleValue(currentCell));
                    break;
                case 27:
                    // Код модификации
                    result.setModificationCode(getStringValue(currentCell));
                    break;
                case 28:
                    // Производитель
                    result.setManufacturer(getStringValue(currentCell));
                    break;
                default:
                    // Unaccounted field
                    break;
            }
            cellNumber++;
        }
        return result;
    }

    @Override
    boolean skipFirstRow() {
        return true;
    }
}
