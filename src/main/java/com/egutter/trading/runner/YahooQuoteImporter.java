package com.egutter.trading.runner;

import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

import static com.egutter.trading.stock.StockMarket.allStockSymbols;
import static com.egutter.trading.stock.StockMarket.merval25StockSymbols;

/**
 * Created by egutter on 11/29/14.
 */
public class YahooQuoteImporter {

    private HistoricPriceRepository repository = new HistoricPriceRepository();

    public static void main(String[] args) throws IOException {
        Optional<DailyQuote> quoteOpt = new YahooQuoteImporter().getLastQuote("NKE");
        DailyQuote quote = quoteOpt.get();
        System.out.println(quote.toString());
    }

    public static void main2(String[] args) throws IOException {
        LocalDate fromDate = new LocalDate(2010, 1, 1);
//        LocalDate toDate = new LocalDate(2018, 1, 1);
        LocalDate toDate = LocalDate.now();
        String[] stockSymbols = StockMarket.merval25StockSymbols();
        HistoricPriceRepository historicPriceRepository = new HistoricPriceRepository();
        Arrays.stream(stockSymbols).forEach(stock -> {
            LocalDate minTradingDate = historicPriceRepository.getMinTradingDate(stock).orElse(toDate.minusDays(10));
            if (fromDate.plusDays(5).isAfter(minTradingDate)) {
                System.out.println("Stock [" + stock + "] already imported minTradeDate = " + minTradingDate);
            } else {
                System.out.println("Stock [" + stock + "] NOT imported minTradeDate = " + minTradingDate);
                new YahooQuoteImporter().runImport(fromDate, toDate, new String[]{stock});
            }
        });

    }

    public Optional<DailyQuote> getLastQuote(String stockName) {
        Stock stock = null;
        try {
            stock = YahooFinance.get(stockName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (stock == null) return Optional.empty();

        StockQuote quote = stock.getQuote();

        System.out.println("Fetched " + stock.getSymbol() + " - " + stock.getName() + " quote " + stock.getQuote());
        return Optional.of(new DailyQuote(LocalDate.fromCalendarFields(quote.getLastTradeTime()),
                quote.getOpen(),
                quote.getPrice(),
                quote.getPrice(),
                quote.getDayLow(),
                quote.getDayHigh(),
                quote.getVolume()));
    }

    public void forEachLastQuote(BiConsumer<String, DailyQuote> applyBlok) {
        try {
            YahooFinance.get(allStockSymbols()).forEach((symbol, stock) -> {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void runImportFromMaxOrFrom(LocalDate fromDate, String[] symbols) {
        LocalDate fromMaxDate = repository.getMaxTradingDate(symbols).orElse(fromDate).plusDays(1);
        runImport(fromMaxDate, symbols);
    }

    public void runImport(LocalDate fromDate) {
        runImport(fromDate, merval25StockSymbols());
    }

    public List<StockPrices> fetchStockPrices(LocalDate fromDate, String[] stockSymbols) {
        Calendar to = Calendar.getInstance();

        Calendar from = Calendar.getInstance();

        from.set(Calendar.YEAR, fromDate.getYear());
        from.set(Calendar.MONTH, fromDate.getMonthOfYear() - 1);
        from.set(Calendar.DAY_OF_MONTH, fromDate.getDayOfMonth());

        List<StockPrices> stockPrices = new ArrayList<>();

        try {
            Map<String, Stock> stocks =  YahooFinance.get(stockSymbols, from, to, Interval.DAILY);
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
                stockPrices.add(new StockPrices(stock.getSymbol(), dailyQuotes));
            }
            return stockPrices;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runImport(LocalDate fromDate, String[] stockSymbols) {
        runImport(fromDate, LocalDate.now(), stockSymbols);
    }

    public void runImport(LocalDate fromDate, LocalDate toDate, String[] stockSymbols) {
        if (toDate.isBefore(fromDate) || toDate.equals(fromDate)) {
            System.out.println("Nothing to import for symbols " + Arrays.toString(stockSymbols));
            return;
        }
        Calendar to = Calendar.getInstance();

        to.set(Calendar.YEAR, toDate.getYear());
        to.set(Calendar.MONTH, toDate.getMonthOfYear() - 1);
        to.set(Calendar.DAY_OF_MONTH, toDate.getDayOfMonth());

        Calendar from = Calendar.getInstance();

        from.set(Calendar.YEAR, fromDate.getYear());
        from.set(Calendar.MONTH, fromDate.getMonthOfYear() - 1);
        from.set(Calendar.DAY_OF_MONTH, fromDate.getDayOfMonth());

        try {
            Map<String, Stock> stocks = YahooFinance.get(stockSymbols, from, to, Interval.DAILY);

            System.out.println("Start import from " + from.getTime() + " to " + to.getTime());
            for (Stock stock : stocks.values()) {
                List<DailyQuote> dailyQuotes = new ArrayList<DailyQuote>();
                try {
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
                } catch (Exception e) {
                    System.out.println("Failed to import " + stock.getSymbol() + " error " + e.getMessage());
//                    throw new RuntimeException(e);
                }
                repository.removeAllBetween(stock.getSymbol(), fromDate, toDate);

                StockPrices prices = new StockPrices(stock.getSymbol(), dailyQuotes);
                System.out.println("Import " + stock.getSymbol() + " - " + stock.getName() + " number of quotes " + dailyQuotes.size());
                repository.insertStockPrices(prices);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

}
