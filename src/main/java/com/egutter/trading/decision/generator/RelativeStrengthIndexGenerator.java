package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class RelativeStrengthIndexGenerator extends MomentumOscillatorGenerator<RelativeStrengthIndex> {

    public RelativeStrengthIndexGenerator(BitString chromosome) {
        super(RelativeStrengthIndex.class, chromosome);
    }
}
