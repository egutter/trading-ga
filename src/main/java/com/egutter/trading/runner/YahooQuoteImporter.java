package com.egutter.trading.runner;

import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.mongodb.WriteResult;
import org.joda.time.LocalDate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.egutter.trading.stock.StockMarket.stockSymbols;

/**
 * Created by egutter on 11/29/14.
 */
public class YahooQuoteImporter {

    private HistoricPriceRepository repository = new HistoricPriceRepository();

    public static void main(String[] args) {
//        new YahooQuoteImporter().runImport();
        DailyQuote lastQuote = new YahooQuoteImporter().getLastQuote("YPFD.BA");
        System.out.println(lastQuote.getTradingDate().isAfter(new HistoricPriceRepository().getMaxTradingDate()));
    }

    public DailyQuote getLastQuote(String stockName) {
        Stock stock = YahooFinance.get(stockName);
        StockQuote quote = stock.getQuote();
        System.out.println("Fetched " + stock.getSymbol() + " - " + stock.getName() + " quote " + stock.getQuote());
        return new DailyQuote(LocalDate.fromCalendarFields(quote.getLastTradeTime()),
                quote.getOpen(),
                quote.getPrice(),
                quote.getPrice(),
                quote.getDayLow(),
                quote.getDayHigh(),
                quote.getVolume());
    }

    public void forEachLastQuote(BiConsumer<String, DailyQuote> applyBlok) {
        YahooFinance.get(stockSymbols()).forEach((symbol, stock) -> {
            StockQuote quote = stock.getQuote();
            DailyQuote dailyQuote = new DailyQuote(LocalDate.fromCalendarFields(quote.getLastTradeTime()),
                    quote.getOpen(),
                    quote.getPrice(),
                    quote.getPrice(),
                    quote.getDayLow(),
                    quote.getDayHigh(),
                    quote.getVolume());
            applyBlok.accept(symbol, dailyQuote);
        });
    }
    public void runImport() {
        LocalDate fromDate = repository.getMaxTradingDate().plusDays(1);
        runImport(fromDate);
    }

    public void runImport(LocalDate fromDate) {
        Calendar to = Calendar.getInstance();

        Calendar from = Calendar.getInstance();

        from.set(Calendar.YEAR, fromDate.getYear());
        from.set(Calendar.MONTH, fromDate.getMonthOfYear() - 1);
        from.set(Calendar.DAY_OF_MONTH, fromDate.getDayOfMonth());

        Map<String, Stock> stocks = YahooFinance.get(stockSymbols(), from, to, Interval.DAILY);

        System.out.println("Start import from " + from.getTime() + " to " + to.getTime());
        for (Stock stock : stocks.values()) {
            List<DailyQuote> dailyQuotes = new ArrayList<DailyQuote>();
            for (HistoricalQuote quote : stock.getHistory()) {
                DailyQuote dailyQuote = new DailyQuote(LocalDate.fromCalendarFields(quote.getDate()),
                        quote.getOpen(),
                        quote.getClose(),
                        quote.getAdjClose(),
                        quote.getLow(),
                        quote.getHigh(),
                        quote.getVolume());

                dailyQuotes.add(dailyQuote);
            }
            repository.removeAllFrom(stock.getSymbol(), fromDate);

            StockPrices prices = new StockPrices(stock.getSymbol(), dailyQuotes);
            System.out.println("Import " + stock.getSymbol() + " - " + stock.getName() + " number of quotes " + dailyQuotes.size());
            repository.insertStockPrices(prices);
        }
    }

}