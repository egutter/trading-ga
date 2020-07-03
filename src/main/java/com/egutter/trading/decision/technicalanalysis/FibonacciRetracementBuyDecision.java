package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.order.OrderExtraInfo;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.google.common.collect.Range;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FibonacciRetracementBuyDecision implements BuyTradingDecision {


    public static final double FIB_RETR_0_236 = 0.236;
    public static final double FIB_RETR_0_382 = 0.382;
    public static final double FIB_RETR_0_5 = 0.5;
    public static final double FIB_RETR_0_618 = 0.618;
    public static final double FIB_RETR_0_786 = 0.786;
    public static final List<Double> FIBONACCI_RETR_LEVELS = Arrays.asList(FIB_RETR_0_236, FIB_RETR_0_382, FIB_RETR_0_5, FIB_RETR_0_618, FIB_RETR_0_786);
    public static final double FIB_EXT_1 = 1;
    public static final double FIB_EXT_1_236 = 1.236;
    public static final double FIB_EXT_1_382 = 1.382;
    public static final double FIB_EXT_1_5 = 1.5;
    public static final double FIB_EXT_1_618 = 1.618;
    public static final double FIB_EXT_1_786 = 1.786;
    public static final List<Double> FIBONACCI_EXT_LEVES = Arrays.asList(FIB_EXT_1, FIB_EXT_1_236, FIB_EXT_1_382, FIB_EXT_1_5, FIB_EXT_1_618, FIB_EXT_1_786);

    private final Range retracementLevel;

    private final StockPrices stockPrices;
    private final LocalDate startOnDate;
    private double buyLevelTrigger;
    private final int highLookback;
    private final int lowLookback;
    private final double sellExtensionFirstLevel;
    private final double sellExtensionSecondLevel;
    private double[] lastHigh;
    private int[] lastHighIndex;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2019, 1, 01);
        StockMarket market = new StockMarketBuilder().buildInMemory(fromDate);
        FibonacciRetracementBuyDecision fib = new FibonacciRetracementBuyDecision(market.getStockPricesFor("SPY"),
                Range.open(new BigDecimal(0.382), new BigDecimal(0.5)),
                0.5,20, 40, FIB_EXT_1_236, FIB_EXT_1_618);
        System.out.println(fib.shouldBuyOn(new LocalDate(2019, 10, 4)));
    }
    public FibonacciRetracementBuyDecision(StockPrices stockPrices,
                                           Range<BigDecimal> retracementLevel,
                                           double buyLevelTrigger,
                                           int highLookback,
                                           int lowLookback,
                                           double sellExtensionFirstLevel,
                                           double sellExtensionSecondLevel) {
        this.retracementLevel = retracementLevel;
        this.stockPrices = stockPrices;
        this.startOnDate = stockPrices.getFirstTradingDate();
        this.buyLevelTrigger = buyLevelTrigger;
        this.highLookback = highLookback;
        this.lowLookback = lowLookback;
        this.sellExtensionFirstLevel = sellExtensionFirstLevel;
        this.sellExtensionSecondLevel = sellExtensionSecondLevel;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        try {
            DailyQuote high = stockPrices.getLastHighFrom(tradingDate, this.highLookback);
            DailyQuote low = stockPrices.getLastLowFrom(tradingDate, this.lowLookback);

            if (low.isAfter(high.getTradingDate())) {
                return DecisionResult.NO;
            }

            Optional<DailyQuote> previousQuote = this.stockPrices.dailyPriceBefore(tradingDate, 1);
            Optional<DailyQuote> quoteAtDate = stockPrices.dailyPriceOn(tradingDate);
            if (!previousQuote.isPresent()) return DecisionResult.NEUTRAL;
            if (!quoteAtDate.isPresent()) return DecisionResult.NEUTRAL;

            if (previousQuote.get().getClosePrice() < quoteAtDate.get().getClosePrice()) return DecisionResult.NO;

            double retracement = (high.getClosePrice() - quoteAtDate.get().getClosePrice()) / (high.getClosePrice() - low.getClosePrice());
            MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

            BigDecimal actualRetracement = new BigDecimal(retracement, mc);

            if (this.retracementLevel.contains(actualRetracement)) {
                OrderExtraInfo buyOrderExtraInfo = new OrderExtraInfo();
//                buyOrderExtraInfo.addSellPrice(high.getClosePrice(), 1);//FIB_RETR_0_5);
                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(this.sellExtensionFirstLevel, high, low), 1);
                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(this.sellExtensionSecondLevel, high, low), 1);
//                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(FIB_EXT_1_618, high, low), 0.5);
//                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(FIB_EXT_1_786, high, low), 1.0);
                buyOrderExtraInfo.setBuyPriceTrigger(sellTakeProfitPrice(this.buyLevelTrigger, high, low));
                buyOrderExtraInfo.setHighQuote(high);
                buyOrderExtraInfo.setLowQuote(low);
                return DecisionResult.yesWithExtraInfo(buyOrderExtraInfo);
            }
        } catch (IllegalArgumentException e) {
            return DecisionResult.NO;
        }
        return DecisionResult.NO;
    }

    private double sellTakeProfitPrice(double level, DailyQuote high, DailyQuote low) {
        return (level * (high.getClosePrice() - low.getClosePrice())) + low.getClosePrice();
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "High days",
                this.highLookback,
                "Low days",
                this.lowLookback,
                "Retracement",
                this.retracementLevel,
                "Buy Level",
                this.buyLevelTrigger,
                "Sell Extension First",
                this.sellExtensionFirstLevel,
                "Sell Extension Second",
                this.sellExtensionSecondLevel);
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }
}
