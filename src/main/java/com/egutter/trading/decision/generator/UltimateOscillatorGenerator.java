package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.UltimateOscillator;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class UltimateOscillatorGenerator extends MomentumOscillatorGenerator<UltimateOscillator> {

    public UltimateOscillatorGenerator(BitString chromosome) {
        super(UltimateOscillator.class, chromosome);
    }

    @Override
    protected int daysBase() {
        return 5;
    }

}
