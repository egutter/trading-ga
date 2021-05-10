package com.egutter.trading.order.condition;

import com.egutter.trading.decision.constraint.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.order.BuyConditionalOrder;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class BuyDecisionConditionsFactory {

    private final List<ConditionalOrderConditionGenerator> conditionGenerators;
    private final Portfolio portfolio;
    private final StockPrices stockPrices;

    public BuyDecisionConditionsFactory(List<ConditionalOrderConditionGenerator> conditionGenerators, Portfolio portfolio, StockPrices stockPrices) {
        this.conditionGenerators = conditionGenerators;
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }

    public static BuyDecisionConditionsFactory empty() {
        return new BuyDecisionConditionsFactory(Collections.emptyList(), Portfolio.empty(), StockPrices.empty());
    }

    public void addConditions(BuyConditionalOrder buyOrder) {
        buyOrder.addCondition(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
        this.conditionGenerators.forEach(generator -> buyOrder.addCondition(generator.generateCondition(stockPrices)));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BuyDecisionConditionsFactory.class.getSimpleName() + "[", "]")
                .add("conditionGenerators=" + conditionGenerators)
                .toString();
    }
}
