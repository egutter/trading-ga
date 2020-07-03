package com.egutter.trading.stock;

import com.google.common.base.Function;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import static com.google.common.collect.Iterables.getLast;

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
    private List<Double> closePrices = new ArrayList<>();

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
        return this.closePrices;
    }

    public List<Double> getClosePrices() {
        return this.closePrices;
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
            days = days + 1;
            dayBefore = tradingDate.minusDays(days);
            dailyQuote = dailyPriceOn(dayBefore);
        }
        return dailyQuote;
    }

    public Optional<DailyQuote> dailyPriceAfter(LocalDate tradingDate, int days) {
        LocalDate lastTradingDate = getLastTradingDate();
        LocalDate dayAfter = tradingDate.plusDays(days);
        Optional<DailyQuote> dailyQuote = dailyPriceOn(dayAfter);
        while (!dailyQuote.isPresent() && dayAfter.isBefore(lastTradingDate)) {
            days = days + 1;
            dayAfter = tradingDate.plusDays(days);
            dailyQuote = dailyPriceOn(dayAfter);
        }
        return dailyQuote;
    }

    private void initLists() {
        this.dailyQuotesList = new ArrayList<>(dailyQuotes.values());
        dailyQuotesList.stream().forEach((dailyQuote -> {
            this.adjustedClosedPrices.add(dailyQuote.getAdjustedClosePrice());
            this.closePrices.add(dailyQuote.getClosePrice());
            this.highPrices.add(dailyQuote.getHighPrice());
            this.lowPrices.add(dailyQuote.getLowPrice());
            this.volumes.add(dailyQuote.getVolume());
            this.tradingDates.add(dailyQuote.getTradingDate());
        }));
    }

    public DailyQuote getLastHighFrom(LocalDate tradingDate, int highLookback) {
        return getMinMaxFrom(this.highPrices, tradingDate, highLookback, -1, (priceDiff) -> priceDiff > 0);
    }

    public DailyQuote getLastLowFrom(LocalDate tradingDate, int lowLookback) {
        return getMinMaxFrom(this.lowPrices, tradingDate, lowLookback, Double.MAX_VALUE, (priceDiff) -> priceDiff < 0);
    }

    private DailyQuote getMinMaxFrom(List<Double> prices, LocalDate tradingDate, int lookback, double startValue, Function<Double, Boolean> compareFunc) {
        int dateIndex = -1;
        for (int i = 0; i < tradingDates.size(); i++) {
            LocalDate aDate = tradingDates.get(i);
            if (aDate.equals(tradingDate)) {
                dateIndex = i;
                break;
            }
        }
        int indexMinusLookback = dateIndex - lookback;
        if (dateIndex == -1 || indexMinusLookback < 0) throw new IllegalArgumentException("Date [" + tradingDate + "] not found or lookback is greater");

        int from = Math.max(indexMinusLookback, 0);
        List<LocalDate> dateSublist = tradingDates.subList(from, dateIndex + 1);
        List<Double> priceSublist = prices.subList(from, dateIndex + 1);

        int priceIndex = -1;
        double minMaxPrice = startValue;
        for (int i = 0; i < priceSublist.size(); i++) {
            Double aPrice = priceSublist.get(i);
            if (compareFunc.apply(aPrice - minMaxPrice)) {
                minMaxPrice = aPrice;
                priceIndex = i;
            }
        }
        LocalDate dateWithMax = dateSublist.get(priceIndex);

        return dailyQuotes.get(dateWithMax);
    }
}
