package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.AverageDirectionalIndex;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class AverageDirectionalIndexGenerator extends MomentumOscillatorGenerator<AverageDirectionalIndex> {

    public AverageDirectionalIndexGenerator(BitString chromosome) {
        super(AverageDirectionalIndex.class, chromosome);
    }
}
