package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.dto.my.sklad.MsProductDto;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class MsProductXlsxConverter extends AbstractXlsxConverter<MsProductDto> {

    private static final String EMPTY = "";
    private static final String RUB = "руб";

    private final StringService stringService;

    @Autowired
    public MsProductXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "ms_products";
    }

    @Override
    void buildHeader(RowContext rowCtx) {
        rowCtx
                .header("Группы").setColumnWidth(10)
                .header("Код").setColumnWidth(10)
                .header("Наименование").setColumnWidth(10)
                .header("Внешний код").setColumnWidth(10)
                .header("Артикул").setColumnWidth(10)
                .header("Единица измерения").setColumnWidth(10)
                .header("Цена продажи").setColumnWidth(10)
                .header("Валюта (Цена продажи)").setColumnWidth(10)
                .header("Старая Цена").setColumnWidth(10)
                .header("Валюта (Старая Цена)").setColumnWidth(10)
                .header("Новая Цена").setColumnWidth(10)
                .header("Валюта (Новая Цена)").setColumnWidth(10)
                .header("Закупочная цена").setColumnWidth(10)
                .header("Валюта (Закупочная цена)").setColumnWidth(10)
                .header("Неснижаемый остаток").setColumnWidth(10)
                .header("Штрихкод EAN13").setColumnWidth(10)
                .header("Штрихкод EAN8").setColumnWidth(10)
                .header("Штрихкод Code128").setColumnWidth(10)
                .header("Описание").setColumnWidth(10)
                .header("Минимальная цена").setColumnWidth(10)
                .header("Валюта (Минимальная цена)").setColumnWidth(10)
                .header("Страна").setColumnWidth(10)
                .header("НДС").setColumnWidth(10)
                .header("Поставщик").setColumnWidth(10)
                .header("Архивный").setColumnWidth(10)
                .header("Вес").setColumnWidth(10)
                .header("Объем").setColumnWidth(10)
                .header("Код модификации").setColumnWidth(10)
                .header("Производитель").setColumnWidth(10)
                .header("Ссылка на изображение").setColumnWidth(10);
    }

    @Override
    void writeRow(MsProductDto product, RowContext rowCtx) {
        rowCtx
                .text(stringService.defaultString(product.getCatalogName()))
                .text(stringService.defaultString(product.getCode()))
                .text(stringService.defaultString(product.getName()))
                .text(stringService.defaultString(product.getExternalCode()))
                .text(stringService.defaultString(product.getArticle()))
                .text(stringService.defaultString(product.getUnit()))
                .text(stringService.defaultString(product.getSellingPrice()))
                .text(RUB)
                .text(stringService.defaultString(product.getOldPrice()))
                .text(RUB)
                .text(stringService.defaultString(product.getNewPrice()))
                .text(RUB)
                .text(stringService.defaultString(product.getPurchasePrice()))
                .text(RUB)
                .text(EMPTY)
                .text(stringService.defaultString(product.getBarcodeEan13()))
                .text(stringService.defaultString(product.getBarcodeEan8()))
                .text(stringService.defaultString(product.getBarcodeCode128()))
                .text(stringService.defaultString(product.getDescription()))
                .text(stringService.defaultString(product.getMinPrice()))
                .text(RUB)
                .text(stringService.defaultString(product.getManufacturerCountry()))
                .text(EMPTY)
                .text(stringService.defaultString(product.getProvider()))
                .text(stringService.defaultString(product.getArchival()))
                .text(stringService.defaultString(product.getWeight()))
                .text(stringService.defaultString(product.getVolume()))
                .text(stringService.defaultString(product.getModificationCode()))
                .text(stringService.defaultString(product.getManufacturer()))
                .text(stringService.defaultString(product.getPhotoUrl()));
    }
}
