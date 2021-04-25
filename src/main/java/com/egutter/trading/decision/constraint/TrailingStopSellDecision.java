package com.egutter.trading.decision.constraint;

import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;

public class TrailingStopSellDecision implements Function<TimeFrameQuote, Boolean> {

    private final BigDecimal stopLossPercentage;
    private final BigDecimal trainingLossPercentage;
    private final BigDecimal stopLossPrice;

    private BigDecimal trailingLossPrice;

    public TrailingStopSellDecision(BigDecimal pricePaid, BigDecimal stopLossPercentage, BigDecimal trainingLossPercentage) {
        this.stopLossPercentage = stopLossPercentage;
        this.trainingLossPercentage = trainingLossPercentage;
        this.stopLossPrice = calculateThresholdPrice(pricePaid, stopLossPercentage);
        this.trailingLossPrice = calculateThresholdPrice(pricePaid, trainingLossPercentage);
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        if (fallsBellowStopPrice(timeFrameQuote) || fallsBellowTrailingPrice(timeFrameQuote)) return true;

        trackNewTrailingPrice(timeFrameQuote);

        return false;
    }

    private void trackNewTrailingPrice(TimeFrameQuote timeFrameQuote) {
        BigDecimal highPrice = new BigDecimal(timeFrameQuote.getQuoteAtDay().getHighPrice());
        this.trailingLossPrice = calculateThresholdPrice(highPrice, trainingLossPercentage);
    }

    private BigDecimal calculateThresholdPrice(BigDecimal highPrice, BigDecimal trainingLossPercentage) {
        return highPrice.multiply(BigDecimal.ONE.subtract(trainingLossPercentage.divide(BigDecimal.valueOf(100.00))));
    }

    public BigDecimal getStopLossPrice() {
        return stopLossPrice;
    }

    public BigDecimal getTrailingLossPrice() {
        return trailingLossPrice;
    }

    private boolean fallsBellowStopPrice(TimeFrameQuote timeFrameQuote) {
        return lowPrice(timeFrameQuote).compareTo(stopLossPrice) < 0;
    }

    private boolean fallsBellowTrailingPrice(TimeFrameQuote timeFrameQuote) {
        return lowPrice(timeFrameQuote).compareTo(trailingLossPrice) < 0;
    }

    private BigDecimal lowPrice(TimeFrameQuote timeFrameQuote) {
        return new BigDecimal(timeFrameQuote.getQuoteAtDay().getLowPrice());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrailingStopSellDecision{");
        sb.append("stopLossPercentage=").append(stopLossPercentage);
        sb.append(", trainingLossPercentage=").append(trainingLossPercentage);
        sb.append(", stopLossPrice=").append(stopLossPrice);
        sb.append(", trailingLossPrice=").append(trailingLossPrice);
        sb.append('}');
        return sb.toString();
    }
}
