package com.egutter.trading.order;

import com.egutter.trading.decision.generator.TrailingStopSellDecisionGenerator;
import com.egutter.trading.decision.technicalanalysis.TriggerBuyConditionalOrderDecision;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.egutter.trading.stock.Trader;

import java.util.Optional;
import java.util.function.Function;

public class ConditionalOrderBuyDecision implements TriggerBuyConditionalOrderDecision {
    private Function<TimeFrameQuote, Boolean> conditionalBuyTrigger;
    private StockPrices stockPrices;
    private BuyDecisionConditionsFactory buyDecisionConditionsFactory;
    private int expireInDays;
    private TrailingStopSellDecisionGenerator stopTrailingLossGenerator;

    public ConditionalOrderBuyDecision(StockPrices stockPrices,
                                       Function<TimeFrameQuote, Boolean> conditionalBuyTrigger,
                                       BuyDecisionConditionsFactory buyDecisionConditionsFactory,
                                       int expireInDays,
                                       TrailingStopSellDecisionGenerator stopTrailingLossGenerator) {
        this.conditionalBuyTrigger = conditionalBuyTrigger;
        this.stockPrices = stockPrices;
        this.buyDecisionConditionsFactory = buyDecisionConditionsFactory;
        this.expireInDays = expireInDays;
        this.stopTrailingLossGenerator = stopTrailingLossGenerator;
    }

    public Optional<ConditionalOrder> generateOrder(TimeFrameQuote timeFrameQuote) {
        if (conditionalBuyTrigger.apply(timeFrameQuote)) {
            BuyConditionalOrder buyOrder = generateBuyConditionalOrder(timeFrameQuote);
            return Optional.of(buyOrder);
        }
        return Optional.empty();
    }

    private BuyConditionalOrder generateBuyConditionalOrder(TimeFrameQuote timeFrameQuote) {
        ConditionalSellOrderFactory conditionalSellOrderFactory = new StopTrailingLossConditionalSellOrderFactory(this.stockPrices.getStockName(), stockPrices, stopTrailingLossGenerator);
        BuyConditionalOrder buyOrder = new BuyConditionalOrder(this.stockPrices.getStockName(), timeFrameQuote.getQuoteAtDay(), Trader.AMOUNT_TO_INVEST, conditionalSellOrderFactory, expireInDays);
        buyDecisionConditionsFactory.addConditions(buyOrder);
        return buyOrder;
    }

    @Override
    public String buyDecisionToString() {
        final StringBuffer sb = new StringBuffer("ConditionalOrderBuyDecision{");
        sb.append(conditionalBuyTrigger).append(", ");
        sb.append(buyDecisionConditionsFactory).append(", ");
        sb.append(stopTrailingLossGenerator);
        sb.append('}');
        return sb.toString();
    }

}
