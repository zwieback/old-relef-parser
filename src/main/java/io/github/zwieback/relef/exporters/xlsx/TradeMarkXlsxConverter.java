package io.github.zwieback.relef.exporters.xlsx;

import io.github.zwieback.relef.entities.TradeMark;
import io.github.zwieback.relef.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subtlelib.poi.api.row.RowContext;

@Service
public class TradeMarkXlsxConverter extends AbstractXlsxConverter<TradeMark> {

    private final StringService stringService;

    @Autowired
    public TradeMarkXlsxConverter(StringService stringService) {
        this.stringService = stringService;
    }

    @Override
    String getSheetName() {
        return "trade_marks";
    }

    @Override
    void buildHeader(RowContext rowCtx) {
        rowCtx.
                header("Название").setColumnWidth(20).
                header("URL").setColumnWidth(30).
                header("Последнее изменение").setColumnWidth(20);
    }

    @Override
    void writeRow(TradeMark tradeMark, RowContext rowCtx) {
        rowCtx.
                text(stringService.defaultString(tradeMark.getName())).
                text(stringService.defaultString(tradeMark.getUrl())).
                text(tradeMark.getLastUpdate().toString());
    }
}
