package com.egutter.trading.tda;

import com.studerw.tda.model.quote.EquityQuote;
import com.studerw.tda.model.quote.EtfQuote;
import com.studerw.tda.model.quote.Quote;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class QuoteAdapter {

    private StockQuote stock;
    private Optional<EtfQuote> etfQuote = Optional.empty();
    private Optional<EquityQuote> equityQuote = Optional.empty();

    public QuoteAdapter(String stockName) {
        try {
            this.stock = YahooFinance.get(stockName).getQuote();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public QuoteAdapter(Quote source) {
        if (EtfQuote.class.isAssignableFrom(source.getClass())) {
            etfQuote = Optional.of((EtfQuote) source);
        } else if (EquityQuote.class.isAssignableFrom(source.getClass())) {
            equityQuote = Optional.of((EquityQuote) source);
        }
    }

    public BigDecimal getLastPrice() {
//        return stock.getPrice();
        return this.equityQuote.isPresent() ?
                equityQuote.get().getLastPrice() :
                etfQuote.get().getLastPrice();
    }

    public BigDecimal getOpenPrice() {
//        return stock.getOpen();
        return this.equityQuote.isPresent() ?
                equityQuote.get().getOpenPrice() :
                etfQuote.get().getOpenPrice();
    }

    public BigDecimal getLastClosePrice() {
//        return stock.getPreviousClose();
        return this.equityQuote.isPresent() ?
                equityQuote.get().getClosePrice() :
                etfQuote.get().getClosePrice();
    }
}
