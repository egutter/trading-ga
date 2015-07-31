package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.UltimateOscillator;
import com.egutter.trading.decision.technicalanalysis.WilliamsR;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class WilliamsRGenerator extends MomentumOscillatorGenerator<WilliamsR> {

    public WilliamsRGenerator(BitString chromosome) {
        super(WilliamsR.class, chromosome);
    }

    @Override
    protected int thresholdOffset(int index) {
        if (index < 9) return -100;
        return -90;
    }

}
