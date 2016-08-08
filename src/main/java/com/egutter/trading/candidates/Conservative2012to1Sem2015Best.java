package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 8/15/15.
 */
public class Conservative2012to1Sem2015Best {
    public static List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 2012-1stSem2015", "0001100010010101010100100000110101111011101", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "001110000011001110101001111011", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011101011000110001001110000110000110110110", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011101011110110000100010011000101001001110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "100100000011011011010001101111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "101000100010101100111111111011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011000000110011010100001011101011010110111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011001101101011110010011000110010110000001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1101101111001100000110101101111100101110101", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110010110111101000011010011001011001001001", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "111001110101100111101010111110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1111101000011100001000100111110111101010110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class))
        );
    }

    ;
}
