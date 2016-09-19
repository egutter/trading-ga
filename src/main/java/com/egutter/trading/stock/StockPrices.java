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

    private Map<LocalDate, DailyQuote> dailyQuotes;
    private List<DailyQuote> dailyQuotesList;
    private List<Double> adjustedClosedPrices = new ArrayList<>();
    private List<Double> highPrices = new ArrayList<>();
    private List<Double> lowPrices = new ArrayList<>();
    private List<Long> volumes = new ArrayList<>();
    private List<LocalDate> tradingDates = new ArrayList<>();

    public StockPrices(String stockName) {
        this.stockName = stockName;
        this.dailyQuotes = new LinkedHashMap<LocalDate, DailyQuote>();
    }

    public StockPrices(String stockName, List<DailyQuote> dailyQuotesSource) {
        this(stockName);
        addAll(dailyQuotesSource);
    }

    public void addDailyQuote(LocalDate tradingDate, DailyQuote dailyQuote) {
        dailyQuotes.put(tradingDate, dailyQuote);
    }
    public void addAll(List<DailyQuote> dailyQuotesSource) {
        for (DailyQuote dailyQuote : dailyQuotesSource) {
            dailyQuotes.put(dailyQuote.getTradingDate(), dailyQuote);
        }
        initLists();
    }

    public List<Double> getAdjustedClosePrices() {
        return this.adjustedClosedPrices;
    }

    public List<Double> getHighPrices() {
        return this.highPrices;
    }

    public List<Double> getLowPrices() {
        return this.lowPrices;
    }

    public List<? extends Number> getVolumes() {
        return this.volumes;
    }

    public List<LocalDate> getTradingDates() {
        return this.tradingDates;
    }

    public String getStockName() {
        return stockName;
    }

    public void forEachDailyPrice(LocalDate startOn, Function<DailyQuote, Object> function, BooleanSupplier shouldStop) {
        DailyQuote startOnQuote = dailyPriceOn(startOn).orElse(getFirstDailyQuote());
        int startOnIndex = dailyQuotesList.indexOf(startOnQuote);
        for (ListIterator<DailyQuote> dailyQuotesIter = dailyQuotesList.listIterator(startOnIndex); dailyQuotesIter.hasNext();){
            DailyQuote dailyQuote = dailyQuotesIter.next();
            function.apply(dailyQuote);
            if (shouldStop.getAsBoolean()) break;
        }
    }

    public DailyQuote getFirstDailyQuote() {
        return dailyQuotesList.get(0);
    }

    public void withDailyPriceOn(LocalDate tradingDate, Function<DailyQuote, Object> function) {
        Optional<DailyQuote> quote = dailyPriceOn(tradingDate);
        if (quote.isPresent()) function.apply(quote.get());
    }

    public Optional<DailyQuote> dailyPriceOn(LocalDate tradingDate) {
        DailyQuote dailyQuote = dailyQuotes.get(tradingDate);
        return Optional.ofNullable(dailyQuote);
    }

    public List<DailyQuote> dailyQuotesFromWithLimit(LocalDate tradingDate, int max) {
        return dailyQuotesList.stream().filter(dailyQuote -> dailyQuote.isAfter(tradingDate)).limit(max).collect(Collectors.toList());
    }

    public LocalDate getLastTradingDate() {
        return getLast(dailyQuotesList).getTradingDate();
    }
    public LocalDate getFirstTradingDate() {
        return getFirstDailyQuote().getTradingDate();
    }

    public List<DailyQuote> getDailyQuotes() {
        return dailyQuotesList;
    }

    public Optional<DailyQuote> dailyPriceBefore(LocalDate tradingDate, int days) {
        LocalDate firstTradingDate = getFirstTradingDate();
        LocalDate dayBefore = tradingDate.minusDays(days);
        Optional<DailyQuote> dailyQuote = dailyPriceOn(dayBefore);
        while (!dailyQuote.isPresent() && dayBefore.isAfter(firstTradingDate)) {
            days = days - 1;
            dayBefore = tradingDate.minusDays(days);
            dailyQuote = dailyPriceOn(dayBefore);
        }
        return dailyQuote;
    }

    private void initLists() {
        this.dailyQuotesList = new ArrayList<>(dailyQuotes.values());
        dailyQuotesList.stream().forEach((dailyQuote -> {
            this.adjustedClosedPrices.add(dailyQuote.getAdjustedClosePrice());
            this.highPrices.add(dailyQuote.getHighPrice());
            this.lowPrices.add(dailyQuote.getLowPrice());
            this.volumes.add(dailyQuote.getVolume());
            this.tradingDates.add(dailyQuote.getTradingDate());
        }));
    }

}
