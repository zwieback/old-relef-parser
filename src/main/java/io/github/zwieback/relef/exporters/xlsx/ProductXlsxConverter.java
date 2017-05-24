package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.Product;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class ProductXlsxConverter extends AbstractXlsxConverter<Product> {

    private final StringService stringService;

    @Autowired
    public ProductXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "products";
    }

    void buildHeader(RowContext rowCtx) {
        rowCtx.
                header("ID").setColumnWidth(10).
                header("ID каталога").setColumnWidth(10).
                header("Код").setColumnWidth(10).
                header("Артикул").setColumnWidth(15).
                header("Штрихкод").setColumnWidth(10).
                header("Страна производитель").setColumnWidth(15).
                header("Название").setColumnWidth(40).
                header("Описание").setColumnWidth(20).
                header("URL").setColumnWidth(25).
                header("URL фото").setColumnWidth(15).
                header("URL кешированного фото").setColumnWidth(15).
                header("Партия / упаковка").setColumnWidth(15).
                header("Вес, кг").setColumnWidth(10).
                header("Объем, м³").setColumnWidth(10).
                header("XML ID").setColumnWidth(25).
                header("Тип продукта").setColumnWidth(10).
                header("Цена").setColumnWidth(10).
                header("Мин. заказ").setColumnWidth(10).
                header("Доступно").setColumnWidth(10).
                header("Старая цена").setColumnWidth(10).
                header("Черная пятница").setColumnWidth(10).
                header("Последнее изменение").setColumnWidth(20);
    }

    void writeRow(Product product, RowContext rowCtx) {
        rowCtx.
                number(product.getId()).
                number(product.getCatalogId()).
                text(stringService.defaultString(product.getCode())).
                text(stringService.defaultString(product.getArticle())).
                text(stringService.defaultString(product.getBarcode())).
                text(stringService.defaultString(product.getManufacturerCountry())).
                text(stringService.defaultString(product.getName())).
                text(stringService.defaultString(product.getDescription())).
                text(stringService.defaultString(product.getUrl())).
                text(stringService.defaultString(product.getPhotoUrl())).
                text(stringService.defaultString(product.getPhotoCachedUrl())).
                text(stringService.defaultString(product.getParty())).
                text(stringService.defaultString(product.getWeight())).
                text(stringService.defaultString(product.getVolume())).
                text(stringService.defaultString(product.getXmlId())).
                text(stringService.defaultString(product.getDataType())).
                text(stringService.defaultString(product.getPrice())).
                text(stringService.defaultString(product.getAmount())).
                text(stringService.defaultString(product.getAvailable())).
                text(stringService.defaultString(product.getOldPrice())).
                text(stringService.defaultString(product.getBlackFriday())).
                text(product.getLastUpdate().toString());
    }
}
