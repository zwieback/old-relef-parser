package io.github.zwieback.relef.exporters;

import io.github.zwieback.relef.entities.TradeMark;
import io.github.zwieback.relef.exporters.xlsx.TradeMarkXlsxConverter;
import io.github.zwieback.relef.repositories.TradeMarkRepository;
import io.github.zwieback.relef.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeMarkExporter extends Exporter {

    private final TradeMarkXlsxConverter tradeMarkXlsxConverter;
    private final TradeMarkRepository tradeMarkRepository;

    @Autowired
    public TradeMarkExporter(DateTimeService dateTimeService,
                             TradeMarkXlsxConverter tradeMarkXlsxConverter,
                             TradeMarkRepository tradeMarkRepository) {
        super(dateTimeService);
        this.tradeMarkXlsxConverter = tradeMarkXlsxConverter;
        this.tradeMarkRepository = tradeMarkRepository;
    }

    private List<TradeMark> findAllTradeMarks() {
        return tradeMarkRepository.findAll();
    }

    @Override
    public byte[] toXlsx() {
        return tradeMarkXlsxConverter.toXlsx(findAllTradeMarks());
    }

    @Override
    public String getXlsxFileName() {
        return super.getFileName("trade_marks_", EXTENSION_XLSX);
    }
}
