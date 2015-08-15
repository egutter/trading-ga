package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.egutter.trading.decision.technicalanalysis.MovingAverageConvergenceDivergence;
import com.egutter.trading.decision.technicalanalysis.UltimateOscillator;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import java.util.List;

import static com.egutter.trading.helper.TestHelper.aStockPortfolio;
import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TradingDecisionFactoryTest {

    private List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators = asList(MovingAverageConvergenceDivergenceGenerator.class,
            UltimateOscillatorGenerator.class,
            MoneyFlowIndexGenerator.class);

    @Test
    public void should_build_buyGenerator_onExperiment() throws Exception {

        TradingDecisionFactory tradingDecisionFactory = new TradingDecisionFactory(aStockPortfolio(),
                aGenome(), tradingDecisionGenerators, true);

        BuyTradingDecision buyTradingDecision = tradingDecisionFactory.generateBuyDecision(aStockPrices());
        assertThat(buyTradingDecision, instanceOf(BuyWhenNoOppositionsTradingDecision.class));

        BuyWhenNoOppositionsTradingDecision buyWhenNoOppositionsTradingDecision = (BuyWhenNoOppositionsTradingDecision) buyTradingDecision;
        assertThat(buyWhenNoOppositionsTradingDecision.getBuyTradingDecisionList().size(), equalTo(5));
        assertThat(buyWhenNoOppositionsTradingDecision.getBuyTradingDecisionList(), hasItems(
                instanceOf(MovingAverageConvergenceDivergence.class),
                instanceOf(UltimateOscillator.class),
                instanceOf(MoneyFlowIndex.class),
                instanceOf(DoNotBuyWhenSameStockInPortfolio.class),
                instanceOf(DoNotBuyInTheLastBuyTradingDays.class)
        ));
    }

    @Test
    public void should_build_buyGenerator_offExperiment() throws Exception {

        TradingDecisionFactory tradingDecisionFactory = new TradingDecisionFactory(aStockPortfolio(),
                aGenome(), tradingDecisionGenerators, false);

        BuyTradingDecision buyTradingDecision = tradingDecisionFactory.generateBuyDecision(aStockPrices());
        assertThat(buyTradingDecision, instanceOf(BuyWhenNoOppositionsTradingDecision.class));

        BuyWhenNoOppositionsTradingDecision buyWhenNoOppositionsTradingDecision = (BuyWhenNoOppositionsTradingDecision) buyTradingDecision;
        assertThat(buyWhenNoOppositionsTradingDecision.getBuyTradingDecisionList().size(), equalTo(4));

        assertThat(buyWhenNoOppositionsTradingDecision.getBuyTradingDecisionList(), hasItems(
                instanceOf(MovingAverageConvergenceDivergence.class),
                instanceOf(UltimateOscillator.class),
                instanceOf(MoneyFlowIndex.class),
                instanceOf(DoNotBuyWhenSameStockInPortfolio.class)
                ));
    }
    @Test
    public void should_build_sellGenerator_offExperiment() throws Exception {

        TradingDecisionFactory tradingDecisionFactory = new TradingDecisionFactory(aStockPortfolio(),
                aGenome(), tradingDecisionGenerators, false);

        SellTradingDecision sellTradingDecision = tradingDecisionFactory.generateSellDecision(aStockPrices());
        assertThat(sellTradingDecision, instanceOf(SellWhenAnyAgreeTradingDecision.class));

        SellWhenAnyAgreeTradingDecision sellWhenAnyAgreeTradingDecision = (SellWhenAnyAgreeTradingDecision) sellTradingDecision;

        List<TradingDecision> sellTradingDecisionList = sellWhenAnyAgreeTradingDecision.getSellTradingDecisionList();
        assertThat(sellTradingDecisionList.size(), equalTo(2));

        assertThat(sellTradingDecisionList, hasItems(instanceOf(SellWhenNoOppositionsTradingDecision.class),
                instanceOf(SellWhenNoOppositionsTradingDecision.class)));

        SellWhenNoOppositionsTradingDecision sellTechnicalDecisionComposite = (SellWhenNoOppositionsTradingDecision) sellTradingDecisionList.get(0);

        assertThat(sellTechnicalDecisionComposite.getSellTradingDecisionList().size(), equalTo(4));
        assertThat(sellTechnicalDecisionComposite.getSellTradingDecisionList(), hasItems(
                instanceOf(DoNotSellWhenNoStockInPorfolio.class),
                instanceOf(MovingAverageConvergenceDivergence.class),
                instanceOf(UltimateOscillator.class),
                instanceOf(MoneyFlowIndex.class)
                ));

        SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = (SellWhenNoOppositionsTradingDecision) sellTradingDecisionList.get(1);

        assertThat(sellAfterAFixedNumberOfDaysComposite.getSellTradingDecisionList().size(), equalTo(3));
        assertThat(sellAfterAFixedNumberOfDaysComposite.getSellTradingDecisionList(), hasItems(instanceOf(DoNotSellWhenNoStockInPorfolio.class),
                instanceOf(SellAfterAFixedNumberOFDays.class),
                instanceOf(SellByProfitThreshold.class)));

    }

    private BitString aGenome() {
        return new BitString("0111111110001111110100001000000010100010010");
    }
}