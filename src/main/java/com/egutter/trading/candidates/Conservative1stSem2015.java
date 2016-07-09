package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 7/30/15.
 */
public class Conservative1stSem2015 {

    public static List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 1stSem 2015", "110100011101011000101110010010", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111001001011010011001010000100", asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110101010100011101001001100011001101001011", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Mar-Abr-Jun 2016", "1111010010100001110101010011000001011100100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111010011000010110101010111011100100011101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "0110000100110010101001111101101100001001101", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1001010000011101100100011100010111101001100", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1001111111100001011000100011000100110001010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111111111110101010001100000011011000111101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1110101011010010011100100110000110111101101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1110101010111010110110011011101011101011101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111101010100101101100011101110010001011011", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111101011100011001110011001011011101101101", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111010110100110110000101101011100100110101", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "0111010110011100111010101011011100000111100", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1000110101001101011000010011010100110010011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111101010001001001010101100011100111000101", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "0100001010110010010110011010000100000111010", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1110010110110111110111110001011100101010101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111111010100101101111010111011100101000101", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "1111010111001011001001110010111010100110101", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "011110001100010011101001101101", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Mar-Abr-Jun 2016", "001101110101001001100001011001", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class))
        );
    }
}
