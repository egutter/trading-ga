package com.egutter.trading.genetic.cache;

import com.egutter.trading.decision.generator.BuyTradingDecisionGenerator;
import com.egutter.trading.repository.FitnessCacheRepository;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Function;
import org.uncommons.maths.binary.BitString;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 12/4/14.
 */
public class CachedFitnesseEvaluatorDecorator implements FitnessEvaluator<BitString> {

    private final FitnessCacheRepository cacheRepository;
    private final StockMarket stockMarket;
    private final List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators;
    private final String baseKey;
    private final FitnessEvaluator<BitString> decoratee;

    public CachedFitnesseEvaluatorDecorator(FitnessEvaluator<BitString> decoratee, StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionsGenerators) {
        this.stockMarket = stockMarket;
        this.tradingDecisionGenerators = tradingDecisionsGenerators;
        this.cacheRepository = new FitnessCacheRepository();
        this.baseKey = buildBaseKey();
        this.decoratee = decoratee;
    }

    @Override
    public double getFitness(final BitString candidate, final List<? extends BitString> population) {
        String key = buildKey(candidate);
        Optional<Double> cachedResult = cacheRepository.get(key);
        return cachedResult.orElseGet(runStockTradingFitnesseAndCacheIt(key, candidate, population));

    }

    private Supplier<Double> runStockTradingFitnesseAndCacheIt(final String key, final BitString candidate, final List<? extends BitString> population) {
        return new Supplier<Double>() {
            @Override
            public Double get() {
                double fitnessResult = decoratee.getFitness(candidate, population);
                cacheRepository.store(key, fitnessResult);
                return fitnessResult;
            }
        };
    }

    @Override
    public boolean isNatural() {
        return decoratee.isNatural();
    }

    private String buildBaseKey() {
        StockPrices marketIndexPrices = this.stockMarket.getMarketIndexPrices();
        return on(".").join(on(".").join(tradingDecisions()),
                marketIndexPrices.getFirstTradingDate(),
                marketIndexPrices.getLastTradingDate());
    }

    private String buildKey(BitString candidate) {
        return this.baseKey + "." + candidate;
    }

    private List<String> tradingDecisions() {
        return transform(this.tradingDecisionGenerators, new Function<Class, String>() {
            @Override
            public String apply(Class c) {
                return c.getSimpleName();
            }
        });
    }

}
