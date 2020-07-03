package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.stock.StockGroup;
import com.google.common.base.Function;
import org.apache.commons.math3.util.Pair;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 1/2/15.
 */
public class Candidate {

    private List<StockGroup> stockGroups = new ArrayList();
    private String description;
    private BitString chromosome;
    private List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators;

    private static Map<Class, String> generatorsKeyMap = new HashMap<Class, String>();
    static {
        generatorsKeyMap.put(BollingerBandsGenerator.class, "BB");
        generatorsKeyMap.put(MovingAverageConvergenceDivergenceGenerator.class, "MACD");
        generatorsKeyMap.put(MoneyFlowIndexGenerator.class, "MFI");
        generatorsKeyMap.put(RelativeStrengthIndexGenerator.class, "RSI");
        generatorsKeyMap.put(AverageDirectionalIndexGenerator.class, "ADX");
        generatorsKeyMap.put(AroonOscilatorGenerator.class, "AROO");
    }

    public Candidate(String description, String chromosome, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        this(description, new BitString(chromosome), tradingDecisionGenerators);
    }

    public Candidate(String description, BitString chromosome, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        this.description = description;
        this.chromosome = chromosome;
        this.tradingDecisionGenerators = tradingDecisionGenerators;
    }

    public <T> Candidate(String description, String chromosome, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators, List<StockGroup> stockGroups) {
        this(description, chromosome, tradingDecisionGenerators);
        this.stockGroups = stockGroups;
    }

    public String key() {
        return on(".").join(chromosome, on(".").join(tradingDecisionNames()));
    }

    private List<String> tradingDecisionNames() {
        return transform(tradingDecisionGenerators, getClassSimpleName());
    }

    private Function<Class<? extends BuyTradingDecisionGenerator>, String> getClassSimpleName() {
        return new Function<Class<? extends BuyTradingDecisionGenerator>, String>() {
            @Override
            public String apply(Class<? extends BuyTradingDecisionGenerator> aClass) {
                return generatorsKeyMap.getOrDefault(aClass, aClass.getSimpleName());
            }
        };
    }

    public BitString getChromosome() {
        return chromosome;
    }

    public List<? extends Class<? extends BuyTradingDecisionGenerator>> getTradingDecisionGenerators() {
        return tradingDecisionGenerators;
    }

    public String getDescription() {
        return description;
    }

    public List<StockGroup> getStockGroups() {
        return stockGroups;
    }

    @Override
    public String toString() {
        return this.getDescription() + ": " + this.key();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().isAssignableFrom(Candidate.class)) return false;
        return this.key().equals(((Candidate)obj).key());
    }

    @Override
    public int hashCode() {
        return this.key().hashCode();
    }
}
