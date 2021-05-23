package com.egutter.trading.decision.constraint;

import com.egutter.trading.order.SellPriceResolver;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;

public class TrailingStopSellDecision implements Function<TimeFrameQuote, Boolean>, SellPriceResolver {

    private final BigDecimal stopLossPercentage;
    private final BigDecimal trainingLossPercentage;
    private final BigDecimal stopLossPrice;
    private final BigDecimal targetWinPrice;
    private BigDecimal trailingLossPrice;
    private BigDecimal targetPriceMultiplicator;

    public TrailingStopSellDecision(BigDecimal pricePaid, BigDecimal stopLossPercentage, BigDecimal trainingLossPercentage) {
        this(pricePaid, stopLossPercentage, trainingLossPercentage, BigDecimal.ZERO);
    }

    public TrailingStopSellDecision(BigDecimal pricePaid, BigDecimal stopLossPercentage, BigDecimal trainingLossPercentage, BigDecimal targetPriceMultiplicator) {
        this.stopLossPercentage = stopLossPercentage;
        this.trainingLossPercentage = trainingLossPercentage;
        this.stopLossPrice = calculateThresholdPrice(pricePaid, stopLossPercentage);
        this.trailingLossPrice = calculateThresholdPrice(pricePaid, trainingLossPercentage);
        this.targetPriceMultiplicator = targetPriceMultiplicator;
        this.targetWinPrice = calculateTargetPrice(pricePaid, trainingLossPercentage);
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        if (fallsBellowStopPrice(timeFrameQuote) ||
                fallsBellowTrailingPrice(timeFrameQuote) ||
                reachTargetPrice(timeFrameQuote)) return true;

        trackNewTrailingPrice(timeFrameQuote);

        return false;
    }

    @Override
    public BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote) {
        BigDecimal openPrice = new BigDecimal(timeFrameQuote.getQuoteAtDay().getOpenPrice());
        if (fallsBellowStopPrice(openPrice) ||
                fallsBellowTrailingPrice(openPrice) ||
                reachTargetPrice(openPrice)) {
            return openPrice;
        }
        if (fallsBellowStopPrice(timeFrameQuote)) {
            return this.stopLossPrice;
        }
        if (reachTargetPrice(timeFrameQuote)) {
            return this.targetWinPrice;
        }
        return this.trailingLossPrice;
    }

    private void trackNewTrailingPrice(TimeFrameQuote timeFrameQuote) {
        this.trailingLossPrice = calculateThresholdPrice(highPrice(timeFrameQuote), trainingLossPercentage);
    }

    private BigDecimal calculateThresholdPrice(BigDecimal highPrice, BigDecimal trainingLossPercentage) {
        return highPrice.multiply(BigDecimal.ONE.subtract(trainingLossPercentage.divide(BigDecimal.valueOf(100.00))));
    }

    private BigDecimal calculateTargetPrice(BigDecimal pricePaid, BigDecimal trainingLossPercentage) {
        return pricePaid.multiply(BigDecimal.ONE.add(trainingLossPercentage.multiply(targetPriceMultiplicator).divide(BigDecimal.valueOf(100.00))));
    }

    public BigDecimal getStopLossPrice() {
        return stopLossPrice;
    }

    public BigDecimal getTrailingLossPrice() {
        return trailingLossPrice;
    }

    public BigDecimal getTargetWinPrice() {
        return targetWinPrice;
    }

    private boolean fallsBellowStopPrice(TimeFrameQuote timeFrameQuote) {
        return fallsBellowStopPrice(lowPrice(timeFrameQuote));
    }

    private boolean fallsBellowStopPrice(BigDecimal price) {
        return price.compareTo(stopLossPrice) < 0;
    }

    private boolean fallsBellowTrailingPrice(TimeFrameQuote timeFrameQuote) {
        return fallsBellowTrailingPrice(lowPrice(timeFrameQuote));
    }

    private boolean reachTargetPrice(TimeFrameQuote timeFrameQuote) {
        return reachTargetPrice(highPrice(timeFrameQuote));
    }

    private boolean reachTargetPrice(BigDecimal price) {
        return !targetPriceMultiplicator.equals(BigDecimal.ZERO) && price.compareTo(targetWinPrice) >= 0;
    }

    private boolean fallsBellowTrailingPrice(BigDecimal price) {
        return price.compareTo(trailingLossPrice) < 0;
    }

    private BigDecimal lowPrice(TimeFrameQuote timeFrameQuote) {
        return new BigDecimal(timeFrameQuote.getQuoteAtDay().getLowPrice());
    }

    private BigDecimal highPrice(TimeFrameQuote timeFrameQuote) {
        return new BigDecimal(timeFrameQuote.getQuoteAtDay().getHighPrice());
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrailingStopSellDecision{");
        sb.append("stopLossPercentage=").append(stopLossPercentage);
        sb.append(", trainingLossPercentage=").append(trainingLossPercentage);
        sb.append(", stopLossPrice=").append(stopLossPrice);
        sb.append(", trailingLossPrice=").append(trailingLossPrice);
        sb.append(", targetWinPrice=").append(targetWinPrice);
        sb.append('}');
        return sb.toString();
    }

}
