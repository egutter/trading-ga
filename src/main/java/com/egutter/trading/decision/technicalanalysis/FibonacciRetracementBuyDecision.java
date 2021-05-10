package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.constraint.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.order.*;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.order.condition.DoNotBreakResistance;
import com.egutter.trading.order.condition.DoNotBuyWhenNextDayIsOutsideRange;
import com.egutter.trading.stock.*;
import com.google.common.base.Joiner;
import com.google.common.collect.Range;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FibonacciRetracementBuyDecision implements BuyTradingDecision, TriggerBuyConditionalOrderDecision {


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
    private BuyDecisionConditionsFactory buyDecisionConditionsFactory;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2019, 1, 01);
        StockMarket market = new StockMarketBuilder().buildInMemory(fromDate);
        FibonacciRetracementBuyDecision fib = new FibonacciRetracementBuyDecision(market.getStockPricesFor("SPY"),
                Range.open(new BigDecimal(0.382), new BigDecimal(0.5)),
                0.5,20, 40, FIB_EXT_1_236, FIB_EXT_1_618, BuyDecisionConditionsFactory.empty());
        System.out.println(fib.shouldBuyOn(new LocalDate(2019, 10, 4)));
    }
    public FibonacciRetracementBuyDecision(StockPrices stockPrices,
                                           Range<BigDecimal> retracementLevel,
                                           double buyLevelTrigger,
                                           int highLookback,
                                           int lowLookback,
                                           double sellExtensionFirstLevel,
                                           double sellExtensionSecondLevel,
                                           BuyDecisionConditionsFactory buyDecisionConditionsFactory) {
        this.retracementLevel = retracementLevel;
        this.stockPrices = stockPrices;
        this.startOnDate = stockPrices.getFirstTradingDate();
        this.buyLevelTrigger = buyLevelTrigger;
        this.highLookback = highLookback;
        this.lowLookback = lowLookback;
        this.sellExtensionFirstLevel = sellExtensionFirstLevel;
        this.sellExtensionSecondLevel = sellExtensionSecondLevel;
        this.buyDecisionConditionsFactory = buyDecisionConditionsFactory;
    }


    public Optional<ConditionalOrder> generateOrder(TimeFrameQuote timeFrameQuote) {
        try {
            DailyQuote quoteAtDay = timeFrameQuote.getQuoteAtDay();
            LocalDate tradingDate = quoteAtDay.getTradingDate();
            DailyQuote high = stockPrices.getLastHighFrom(tradingDate, this.highLookback);
            DailyQuote low = stockPrices.getLastLowFrom(tradingDate, this.lowLookback);

            if (low.isAfter(high.getTradingDate())) {
                return Optional.empty();
            }

            DailyQuote previousQuote = timeFrameQuote.getQuoteAtPreviousDay();

            if (previousQuote.getClosePrice() < quoteAtDay.getClosePrice()) return Optional.empty();

            BigDecimal closePriceRetracement = calculateRetracement(high, low, quoteAtDay.getClosePrice());
            BigDecimal lowPriceRetracement = calculateRetracement(high, low, quoteAtDay.getLowPrice());

            if (this.retracementLevel.contains(closePriceRetracement) ||
                    this.retracementLevel.contains(lowPriceRetracement)) {

                BuyConditionalOrder buyOrder = generateBuyConditionalOrder(quoteAtDay, high, low, closePriceRetracement);
                return Optional.of(buyOrder);
            }
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    private BuyConditionalOrder generateBuyConditionalOrder(DailyQuote quoteAtDay, DailyQuote high, DailyQuote low, BigDecimal closePriceRetracement) {
        BigDecimal sellTargetPrice = BigDecimal.valueOf(sellTakeProfitPrice(this.sellExtensionFirstLevel, high, low));
        BigDecimal resistancePrice = resistancePrice(quoteAtDay, closePriceRetracement);

        ConditionalSellOrderFactory conditionalSellOrderFactory = new TargetResistancePriceConditionalSellOrderFactory(this.stockPrices.getStockName(), sellTargetPrice, resistancePrice);
        BuyConditionalOrder buyOrder = new BuyConditionalOrder(this.stockPrices.getStockName(), quoteAtDay, Trader.AMOUNT_TO_INVEST, conditionalSellOrderFactory);
        buyDecisionConditionsFactory.addConditions(buyOrder);
        buyOrder.addCondition(new DoNotBreakResistance(resistancePrice));
        buyOrder.addCondition(new DoNotBuyWhenNextDayIsOutsideRange(Range.open(resistancePrice, sellTargetPrice)));
        return buyOrder;
    }

    private BigDecimal resistancePrice(DailyQuote quoteAtDay, BigDecimal closePriceRetracement) {
        return BigDecimal.valueOf(this.retracementLevel.contains(closePriceRetracement) ?
                quoteAtDay.getClosePrice() :
                quoteAtDay.getLowPrice());
    }

    private BigDecimal calculateRetracement(DailyQuote high, DailyQuote low, double closePrice) {
        MathContext mc = new MathContext(3, RoundingMode.HALF_UP);
        return new BigDecimal((high.getHighPrice() - closePrice) / (high.getHighPrice() - low.getLowPrice()), mc);
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

            double retracement = (high.getHighPrice() - quoteAtDate.get().getClosePrice()) / (high.getHighPrice() - low.getLowPrice());
            MathContext mc = new MathContext(3, RoundingMode.HALF_UP);

            BigDecimal actualRetracement = new BigDecimal(retracement, mc);

            if (this.retracementLevel.contains(actualRetracement)) {
                OrderExtraInfo buyOrderExtraInfo = new OrderExtraInfo();
//                buyOrderExtraInfo.addSellPrice(high.getClosePrice(), 1);//FIB_RETR_0_5);
                buyOrderExtraInfo.addTriggerSellPrice(sellTakeProfitPrice(this.sellExtensionFirstLevel, high, low), 1);
//                buyOrderExtraInfo.addTriggerSellPrice(sellTakeProfitPrice(this.sellExtensionSecondLevel, high, low), 1);
//                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(FIB_EXT_1_618, high, low), 0.5);
//                buyOrderExtraInfo.addSellPrice(sellTakeProfitPrice(FIB_EXT_1_786, high, low), 1.0);

                // FIB EXTENSION
                buyOrderExtraInfo.setBuyPriceTrigger(buyLevelTriggerPrice(this.buyLevelTrigger, high, low));
                // HIGH PRICE
//                buyOrderExtraInfo.setBuyPriceTrigger(quoteAtDate.get().getHighPrice());

                buyOrderExtraInfo.setHighQuote(high);
                buyOrderExtraInfo.setLowQuote(low);
                return DecisionResult.yesWithExtraInfo(buyOrderExtraInfo);
            }
        } catch (IllegalArgumentException e) {
            return DecisionResult.NO;
        }
        return DecisionResult.NO;
    }

    private double buyLevelTriggerPrice(double buyLevelTrigger, DailyQuote high, DailyQuote low) {
        return (high.getHighPrice() - (buyLevelTrigger * (high.getHighPrice() - low.getLowPrice())));
    }

    private double sellTakeProfitPrice(double level, DailyQuote high, DailyQuote low) {
        return (level * (high.getHighPrice() - low.getLowPrice())) + low.getLowPrice();
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
