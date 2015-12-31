package com.egutter.trading.genetic;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.InactiveTradingDecision;
import com.egutter.trading.decision.generator.BuyTradingDecisionGenerator;
import com.egutter.trading.decision.factory.GeneticsTradingDecisionFactory;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Predicate;

import java.util.Collections;

import static com.google.common.collect.Iterables.all;

/**
 * Created by egutter on 3/20/14.
 */
public class GenomeCandidateValidator {
    private GeneticsTradingDecisionFactory tradingDecisionGenerator;

    public GenomeCandidateValidator(GeneticsTradingDecisionFactory tradingDecisionGenerator) {
        this.tradingDecisionGenerator = tradingDecisionGenerator;
    }

    public boolean isInvalid() {
        return allBuyDecisionsAreInactive();
    }

    private boolean allBuyDecisionsAreInactive() {
        return all(tradingDecisionGenerator.getBuyGeneratorChain(), new Predicate<BuyTradingDecisionGenerator>() {
            @Override
            public boolean apply(BuyTradingDecisionGenerator tradingDecisionGenerator) {
                return isInactive(tradingDecisionGenerator.generateBuyDecision(emptyStockPrices()));
            }
        });
    }

    private boolean isInactive(BuyTradingDecision tradingDecision) {
        return tradingDecision.getClass().isAssignableFrom(InactiveTradingDecision.class);
    }

    private StockPrices emptyStockPrices() {
        return new StockPrices("NA", Collections.<DailyQuote>emptyList());
    }
}
