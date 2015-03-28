package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class MoneyFlowIndexGenerator extends MomentumOscillatorGenerator<MoneyFlowIndex> {

    public MoneyFlowIndexGenerator(BitString chromosome) {
        super(MoneyFlowIndex.class, chromosome);
    }

}
