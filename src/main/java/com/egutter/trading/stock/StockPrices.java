package com.egutter.trading.stock;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 2/12/14.
 */
public class StockPrices {

    private String stockName;

    private List<DailyQuote> dailyQuotes;
    private Map<LocalDate, Optional<DailyQuote>> quoteCache = new WeakHashMap<LocalDate, Optional<DailyQuote>>();

    public StockPrices(String stockName, List<DailyQuote> dailyQuotes) {
        this.stockName = stockName;
        this.dailyQuotes = dailyQuotes;
    }

    public List<Double> getAdjustedClosePrices() {
        return transform(dailyQuotes, collectAdjustedClosePrice());
    }

    public List<Double> getHighPrices() {
        return transform(dailyQuotes, collectHighPrice());
    }

    public List<Double> getLowPrices() {
        return transform(dailyQuotes, collectLowPrice());
    }

    public List<? extends Number> getVolumes() {
        return transform(dailyQuotes, collectVolume());
    }

    public List<LocalDate> getTradingDates() {
        return transform(dailyQuotes, collectTradingDates());
    }

    private Function<DailyQuote, LocalDate> collectTradingDates() {
        return new Function<DailyQuote, LocalDate>() {
            @Override
            public LocalDate apply(DailyQuote dailyQuote) {
                return dailyQuote.getTradingDate();
            }
        };
    }

    private Function<DailyQuote, Double> collectAdjustedClosePrice() {
        return new Function<DailyQuote, Double>() {
            @Override
            public Double apply(DailyQuote dailyQuote) {
                return dailyQuote.getAdjustedClosePrice();
            }
        };
    }

    private Function<DailyQuote, Double> collectHighPrice() {
        return new Function<DailyQuote, Double>() {
            @Override
            public Double apply(DailyQuote dailyQuote) {
                return dailyQuote.getHighPrice();
            }
        };
    }

    private Function<? super DailyQuote, ? extends Number> collectVolume() {
        return new Function<DailyQuote, Long>() {
            @Override
            public Long apply(DailyQuote dailyQuote) {
                return dailyQuote.getVolume();
            }
        };
    }

    private Function<DailyQuote, Double> collectLowPrice() {
        return new Function<DailyQuote, Double>() {
            @Override
            public Double apply(DailyQuote dailyQuote) {
                return dailyQuote.getLowPrice();
            }
        };
    }

    public String getStockName() {
        return stockName;
    }

    public void forEachDailyPrice(LocalDate startOn, Function<DailyQuote, Object> function, BooleanSupplier shouldStop) {
        DailyQuote startOnQuote = dailyQuotes.stream().filter(quote -> quote.isOn(startOn)).findFirst().orElse(dailyQuotes.get(0));
        int startOnIndex = dailyQuotes.indexOf(startOnQuote);
        for (ListIterator<DailyQuote> dailyQuotesIter = dailyQuotes.listIterator(startOnIndex); dailyQuotesIter.hasNext();){
            DailyQuote dailyQuote = dailyQuotesIter.next();
            function.apply(dailyQuote);
            if (shouldStop.getAsBoolean()) break;
        }
    }

    public void withDailyPriceOn(LocalDate tradingDate, Function<DailyQuote, Object> function) {
        Optional<DailyQuote> quote = dailyPriceOn(tradingDate);
        if (quote.isPresent()) function.apply(quote.get());
    }

    public Optional<DailyQuote> dailyPriceOn(LocalDate tradingDate) {
        if (quoteCache.containsKey(tradingDate)) return quoteCache.get(tradingDate);

        Optional<DailyQuote> quoteFound = dailyQuotes.stream().filter(dailyQuote -> dailyQuote.isOn(tradingDate)).findFirst();
        quoteCache.put(tradingDate, quoteFound);

        return quoteFound;
    }

    public List<DailyQuote> dailyQuotesFromWithLimit(LocalDate tradingDate, int max) {
        return dailyQuotes.stream().filter(dailyQuote -> dailyQuote.isAfter(tradingDate)).limit(max).collect(Collectors.toList());
    }

    public LocalDate getLastTradingDate() {
        return getLast(dailyQuotes).getTradingDate();
    }
    public LocalDate getFirstTradingDate() {
        return dailyQuotes.get(0).getTradingDate();
    }

    public List<DailyQuote> getDailyQuotes() {
        return dailyQuotes;
    }

}
