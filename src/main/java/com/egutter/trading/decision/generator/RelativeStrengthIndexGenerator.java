package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

import java.util.concurrent.ConcurrentMap;

import static com.google.common.collect.Range.atLeast;
import static com.google.common.collect.Range.atMost;

/**
 * Created by egutter on 2/12/14.
 */
public class RelativeStrengthIndexGenerator extends MomentumOscillatorGenerator<RelativeStrengthIndex> {

    public RelativeStrengthIndexGenerator(BitString chromosome) {
        super(RelativeStrengthIndex.class, chromosome);
    }
}
